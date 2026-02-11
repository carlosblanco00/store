package co.com.inventory.r2dbc;

import co.com.inventory.model.inventory.Inventory;
import co.com.inventory.model.inventory.gateways.InventoryGateway;
import co.com.inventory.model.product.Product;
import co.com.inventory.model.product.ProductInventory;
import co.com.inventory.r2dbc.entity.InventoryEntity;
import co.com.inventory.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.function.Function;

@Repository
public class InventoryRepositoryAdapter extends ReactiveAdapterOperations<
        Inventory,
        InventoryEntity,
        UUID,
        InventoryRepository
        > implements InventoryGateway {

    protected InventoryRepositoryAdapter(InventoryRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Inventory.class));
    }

    @Override
    public Mono<ProductInventory> findInventory(Product x) {
        return Mono.just(x.id())
                .flatMap(id -> repository.findByProductId(id))
                .map(this::toEntity)
                .map(i -> new ProductInventory(x,i));
    }

    @Override
    public Mono<Integer> updateStock(ProductInventory pi) {
        return repository.updateStock(pi.inventory().getId(), pi.product().id(), pi.inventory().getQuantity());
    }
}
