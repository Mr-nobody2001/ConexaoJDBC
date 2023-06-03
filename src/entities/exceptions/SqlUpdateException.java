package entities.exceptions;

public class SqlUpdateException extends RuntimeException {
    public SqlUpdateException(String message) {
        super(message);
    }
}
