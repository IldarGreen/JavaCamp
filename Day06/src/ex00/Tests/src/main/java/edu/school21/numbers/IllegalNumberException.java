package edu.school21.numbers;

public class IllegalNumberException extends RuntimeException{
    public IllegalNumberException() {
        super();
    }

    public IllegalNumberException(String message) {
        super(message);
    }

    public IllegalNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalNumberException(Throwable cause) {
        super(cause);
    }

    protected IllegalNumberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
