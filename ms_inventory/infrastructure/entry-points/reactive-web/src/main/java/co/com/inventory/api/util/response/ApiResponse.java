package co.com.inventory.api.util.response;

import co.com.inventory.api.util.error.ApiError;
import co.com.inventory.api.util.service.ManagementErrorService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

    private Meta meta;
    private Data<?>[] data;
    private String message;
    private ApiError[] errors;

    public static ApiResponse createOnError(String errorCode, String msj){
        return new ApiResponse()
                .setErrors(errorCode)
                .setMessage(msj);
    }

    public ApiResponse setErrors(String key) {
        var error = ManagementErrorService.getApiError(key);
        this.errors = new ApiError[]{error};
        return this;
    }

    public ApiResponse setMessage(String mjs) {
        this.message = mjs;
        return this;
    }
}
