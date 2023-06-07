package entities.exceptions;

public class SqlInsertException extends RuntimeException {
    public SqlInsertException(String message) {
        super(message);
    }
}
