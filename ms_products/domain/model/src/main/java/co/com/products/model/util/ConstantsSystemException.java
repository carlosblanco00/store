package co.com.products.model.util;

import lombok.Getter;

@Getter
public enum ConstantsSystemException {
    INTERNAL_ERROR_EXCEPTION(SystemCode.SERR_500_01, SystemMessages.INTERNAL_ERROR),
    BAD_REQUEST_EXCEPTION(SystemCode.SERR_400_01, SystemMessages.BAD_REQUEST);

    private final String errorCode;
    private final String message;


    ConstantsSystemException(String errorCode,String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    private static class SystemMessages {
        public static final String INTERNAL_ERROR = "Error interno del servidor.";
        public static final String BAD_REQUEST = "Body requerido.";

    }
    private static class SystemCode {
        //NOT FOUNT
        public static final String SERR_500_01 = "SERR-500-01";
        public static final String SERR_400_01 = "SERR-400-01";

    }
}
