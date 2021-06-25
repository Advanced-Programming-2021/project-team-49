package exception;

public class StopAttackException extends RuntimeException {

    public StopAttackException() {
        super();
    }

    public StopAttackException(String message) {
        super(message);
    }

    public StopAttackException(String message, Throwable cause) {
        super(message, cause);
    }

    public StopAttackException(Throwable cause) {
        super(cause);
    }
}
