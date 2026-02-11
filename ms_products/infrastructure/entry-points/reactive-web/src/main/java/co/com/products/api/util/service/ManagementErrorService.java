package co.com.products.api.util.service;

import co.com.products.api.util.error.ApiError;
import co.com.products.model.util.ConstantsBusinessException;
import co.com.products.model.util.ConstantsSystemException;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class ManagementErrorService {

    private static final Map<String, ApiError> errors = new HashMap<>();

    static {
        errors.put(ConstantsBusinessException.PRODUCT_ALREADY_EXISTS_EXCEPTION.getErrorCode(),
                new ApiError(HttpURLConnection.HTTP_CONFLICT,
                        ConstantsBusinessException.PRODUCT_ALREADY_EXISTS_EXCEPTION.getErrorCode(),
                        ConstantsBusinessException.PRODUCT_ALREADY_EXISTS_EXCEPTION.getMessage()));
        errors.put(ConstantsBusinessException.PRODUCT_NOT_FOUNT_EXCEPTION.getErrorCode(),
                new ApiError(HttpURLConnection.HTTP_NOT_FOUND,
                        ConstantsBusinessException.PRODUCT_NOT_FOUNT_EXCEPTION.getErrorCode(),
                        ConstantsBusinessException.PRODUCT_NOT_FOUNT_EXCEPTION.getMessage()));
    }

    public static ApiError getApiError(String key) {
        var error = errors.get(key);
        if (error != null){
            return error;
        } else {
            return new ApiError(HttpURLConnection.HTTP_INTERNAL_ERROR,
                    ConstantsSystemException.INTERNAL_ERROR_EXCEPTION.getErrorCode(),
                    ConstantsSystemException.INTERNAL_ERROR_EXCEPTION.getMessage());
        }

    }
}
