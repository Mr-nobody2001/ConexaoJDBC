package entities.exceptions;

public class IntegrityConstraintException extends RuntimeException {
    public IntegrityConstraintException(String message) {
        super(message);
    }
}
