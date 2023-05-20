package filmaro.com.exception;

public class DataBaseComunicationException extends RuntimeException {
    public DataBaseComunicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
