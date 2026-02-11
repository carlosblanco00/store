package co.com.products.model.util;

import lombok.Getter;

@Getter
public enum ConstantsBusinessException {


    PRODUCT_NOT_FOUNT_EXCEPTION(BusinessCode.BERR_404_01, BusinessMessages.PRODUCT_NOT_FOUNT),
    PRODUCT_ALREADY_EXISTS_EXCEPTION(BusinessCode.BERR_409_01, BusinessMessages.PRODUCT_ALREADY_EXISTS);

    private final String errorCode;
    private final String message;


    ConstantsBusinessException(String errorCode,String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    private static class BusinessMessages {
        public static final String PRODUCT_NOT_FOUNT = "Producto no encontrado.";
        public static final String PRODUCT_ALREADY_EXISTS = "El producto ya existe.";


    }

    private static class BusinessCode {
        //NOT FOUNT
        public static final String BERR_404_01 = "BERR-404_01";

        //CONFLICT
        public static final String BERR_409_01 = "BERR-409-01";

    }
}

