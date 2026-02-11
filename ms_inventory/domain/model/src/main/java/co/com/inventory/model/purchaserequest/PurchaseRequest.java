package co.com.inventory.model.purchaserequest;


import java.util.List;

public record PurchaseRequest(
        String requestId,
        String customerRef,
        String currency,
        List<PurchaseItem> purchaseItems
) {}
