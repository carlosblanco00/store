package co.com.inventory.model.inventory.gateways;

import co.com.inventory.model.product.Product;
import co.com.inventory.model.product.ProductInventory;
import reactor.core.publisher.Mono;

public interface InventoryGateway {
    Mono<ProductInventory> findInventory(Product x);

    Mono<Integer> updateStock(ProductInventory pi);
}
