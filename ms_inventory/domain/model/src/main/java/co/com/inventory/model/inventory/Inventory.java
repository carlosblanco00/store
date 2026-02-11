package co.com.inventory.model.inventory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Inventory {
    private UUID id;
    private UUID productId;
    private BigDecimal quantity;
    private BigDecimal costUnit;
    private String currency;
    private boolean isActive;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
}
