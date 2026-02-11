package co.com.inventory.model.event;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class InventoryEvent {

    private UUID eventId;
    private String eventType; // "DECREASE", "INCREASE", "RESERVATION"
    private LocalDateTime timestamp;

    private String productId;
    private BigDecimal quantity;
    private BigDecimal newQuantity;

}
