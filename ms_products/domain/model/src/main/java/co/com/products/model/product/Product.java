package co.com.products.model.product;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Product {

    private UUID id;
    private String name;
    private String description;
    private String currency;
    private BigDecimal price;
    private BigDecimal cost;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

