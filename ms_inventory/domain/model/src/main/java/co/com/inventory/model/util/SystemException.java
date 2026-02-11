package co.com.inventory.model.util;

public class SystemException  extends RuntimeException {
    private final ConstantsSystemException error;

    public SystemException(ConstantsSystemException error) {
        super(error.getMessage());
        this.error = error;
    }
}
