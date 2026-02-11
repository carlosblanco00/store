package co.com.inventory.model.product;

import co.com.inventory.model.inventory.Inventory;

public record ProductInventory(
        Product product,
        Inventory inventory
) {
}
