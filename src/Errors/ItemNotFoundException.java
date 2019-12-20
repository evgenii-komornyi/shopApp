package Errors;

public class ItemNotFoundException extends Throwable {
    public ItemNotFoundException(String message) {
        super(message);
    }
}
