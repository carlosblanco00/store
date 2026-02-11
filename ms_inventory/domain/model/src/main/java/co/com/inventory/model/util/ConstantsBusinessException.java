package co.com.inventory.model.util;

import lombok.Getter;

@Getter
public enum ConstantsBusinessException {


    PRODUCT_NOT_FOUNT_EXCEPTION(BusinessCode.BERR_404_01, BusinessMessages.PRODUCT_NOT_FOUNT),
    PRODUCT_ALREADY_EXISTS_EXCEPTION(BusinessCode.BERR_409_01, BusinessMessages.PRODUCT_ALREADY_EXISTS),
    INSUFFICIENT_STOCK_EXCEPTION(BusinessCode.BERR_404_02, BusinessMessages.INSUFFICIENT_STOCK),
    INVENTORY_NOT_FOUND_EXCEPTION(BusinessCode.BERR_404_03, BusinessMessages.INVENTORY_NOT_FOUND);;

    private final String errorCode;
    private final String message;


    ConstantsBusinessException(String errorCode,String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    private static class BusinessMessages {
        public static final String PRODUCT_NOT_FOUNT = "Producto no encontrado.";
        public static final String PRODUCT_ALREADY_EXISTS = "El producto ya existe.";
        public static final String INSUFFICIENT_STOCK = "No existe Stock disponible.";
        public static final String INVENTORY_NOT_FOUND = "Inventario no existe para este producto.";


    }

    private static class BusinessCode {
        //NOT FOUNT
        public static final String BERR_404_01 = "BERR-404_01";
        public static final String BERR_404_02 = "BERR-404_02";
        public static final String BERR_404_03 = "BERR-404_03";

        //CONFLICT
        public static final String BERR_409_01 = "BERR-409-01";

    }
}

