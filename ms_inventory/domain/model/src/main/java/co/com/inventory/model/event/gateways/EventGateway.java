package co.com.inventory.model.event.gateways;

import co.com.inventory.model.event.InventoryEvent;

public interface EventGateway {
    void emitEvent(InventoryEvent event);
}
