package co.com.products.r2dbc.product.entity;

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

@Table("product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ProductEntity {

    @Id
    public UUID id;
    public String name;
    public String description;
    public BigDecimal price;
    public BigDecimal cost;
    public String status;
    @Column("create_at")
    public LocalDateTime createdAt;
    @Column("update_at")
    public LocalDateTime updatedAt;
}
