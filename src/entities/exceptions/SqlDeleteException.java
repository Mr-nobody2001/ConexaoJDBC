package entities.exceptions;

public class SqlDeleteException extends RuntimeException {
    public SqlDeleteException(String message) {
        super(message);
    }
}
