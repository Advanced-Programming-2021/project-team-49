package exception;

public class GameErrorException extends RuntimeException {

    public GameErrorException() {
        super();
    }

    public GameErrorException(String message) {
        super(message);
    }

    public GameErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public GameErrorException(Throwable cause) {
        super(cause);
    }
}
