package exception;

public class EndPhaseException extends RuntimeException {

    public EndPhaseException() {
        super();
    }

    public EndPhaseException(String message) {
        super(message);
    }

    public EndPhaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public EndPhaseException(Throwable cause) {
        super(cause);
    }
}
