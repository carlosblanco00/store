package co.com.inventory.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Table("inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class InventoryEntity {
    @Id
    public UUID id;
    @Column("product_id")
    private UUID productId;
    private BigDecimal quantity;
    @Column("cost_unit")
    private BigDecimal costUnit;
    private String currency;
    private boolean isActive;
    @Column("create_at")
    public LocalDateTime createdAt;
    @Column("update_at")
    public LocalDateTime updatedAt;
}
