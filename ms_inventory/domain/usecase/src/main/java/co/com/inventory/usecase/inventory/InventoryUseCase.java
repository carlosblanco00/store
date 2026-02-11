package co.com.inventory.usecase.inventory;

import co.com.inventory.model.event.InventoryEvent;
import co.com.inventory.model.event.gateways.EventGateway;
import co.com.inventory.model.inventory.gateways.InventoryGateway;
import co.com.inventory.model.product.ProductInventory;
import co.com.inventory.model.product.gateways.ProductGateway;
import co.com.inventory.model.purchaserequest.PurchaseItem;
import co.com.inventory.model.purchaserequest.PurchaseRequest;
import co.com.inventory.model.util.BusinessException;
import co.com.inventory.model.util.ConstantsBusinessException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class InventoryUseCase {
    private final ProductGateway productGateway;
    private final InventoryGateway inventoryGateway;
    private final EventGateway eventGateway;

    public Mono<ProductInventory> findProductInventory(UUID id) {

        return productGateway.getByID(id)
                .switchIfEmpty(Mono.error(
                        new BusinessException(ConstantsBusinessException.PRODUCT_NOT_FOUNT_EXCEPTION)))
                .flatMap(inventoryGateway::findInventory)
                .switchIfEmpty(Mono.error(
                        new BusinessException(ConstantsBusinessException.INVENTORY_NOT_FOUND_EXCEPTION)));
    }

    public Flux<ProductInventory> decreaseStockOnPurchase(PurchaseRequest purchaseRequest) {
        Map<UUID, BigDecimal> qtyByProduct = quantityPerKey(purchaseRequest);

        return Flux.fromIterable(qtyByProduct.entrySet())
                .concatMap(entry -> this.findProductInventory(entry.getKey())
                        .flatMap(pi ->
                                validateAndDecrease(pi, entry.getValue()))
                        .doOnNext(pi -> Mono.fromRunnable(() -> purchaseEvent(pi, entry.getValue())))

                );
    }

    private void purchaseEvent(ProductInventory productInventory, BigDecimal quantity) {
        var event = InventoryEvent.builder()
                .eventId(UUID.randomUUID())
                .eventType("DECREASE")
                .productId(productInventory.product().id().toString())
                .quantity(quantity)
                .newQuantity(productInventory.inventory().getQuantity())
                .build();
        eventGateway.emitEvent(event);
    }


    private Map<UUID, BigDecimal> quantityPerKey(PurchaseRequest request) {
        return request.purchaseItems().stream()
                .collect(Collectors.toMap(
                        PurchaseItem::productId,
                        PurchaseItem::quantity,
                        BigDecimal::add
                ));
    }


    private Mono<ProductInventory> validateAndDecrease(ProductInventory pi, BigDecimal requestedQty) {
        BigDecimal available = pi.inventory().getQuantity();
        if (available.compareTo(requestedQty) < 0) {
            return Mono.error(new BusinessException(ConstantsBusinessException.INSUFFICIENT_STOCK_EXCEPTION));
        }

        BigDecimal newAvailable = available.subtract(requestedQty);
        pi.inventory().setQuantity(newAvailable);

        return inventoryGateway.updateStock(pi)
                .filter(i -> i > 0)
                .map(i -> pi)
                .switchIfEmpty(Mono.error(new BusinessException(ConstantsBusinessException.INSUFFICIENT_STOCK_EXCEPTION)));
    }


}
