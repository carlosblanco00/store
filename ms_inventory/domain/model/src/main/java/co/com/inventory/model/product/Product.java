package co.com.inventory.model.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


public record Product(
        UUID id,
        String name,
        String description,
        String currency,
        BigDecimal price,
        BigDecimal cost,
        String status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}

