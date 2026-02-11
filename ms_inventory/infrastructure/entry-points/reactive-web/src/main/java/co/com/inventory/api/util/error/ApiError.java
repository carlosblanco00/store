package co.com.inventory.api.util.error;

import lombok.Data;

import java.util.UUID;

@Data
public class ApiError {
    private String id;
    private int status;
    private String code;
    private String detai;

    public ApiError( int status, String code, String detai) {
        this.id = UUID.randomUUID().toString();
        this.status = status;
        this.code = code;
        this.detai = detai;
    }
}
