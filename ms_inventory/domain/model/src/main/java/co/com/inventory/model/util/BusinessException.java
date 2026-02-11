package co.com.inventory.model.util;


import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final ConstantsBusinessException error;

    public BusinessException(ConstantsBusinessException error) {
        super(error.getMessage());
        this.error = error;
    }



}
