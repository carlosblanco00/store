package co.com.inventory.event;

import co.com.inventory.model.event.InventoryEvent;
import co.com.inventory.model.event.gateways.EventGateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class EventAdapter implements EventGateway {

    private static final Logger log = LoggerFactory.getLogger(EventAdapter.class);

    @Override
    public void emitEvent(InventoryEvent event) {

        log.info("EVENT => {}", event);

    }
}
