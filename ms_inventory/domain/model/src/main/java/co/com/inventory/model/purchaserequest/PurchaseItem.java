package co.com.inventory.model.purchaserequest;

import java.math.BigDecimal;
import java.util.UUID;

public record PurchaseItem(
        String itemId,
        BigDecimal quantity,
        BigDecimal unitPrice,
        UUID productId
) {}
