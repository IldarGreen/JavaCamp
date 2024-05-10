package edu.school21.chat.exceptions;

public class NotSavedSubEntityException extends RuntimeException {
    public NotSavedSubEntityException() {
        super();
    }

    public NotSavedSubEntityException(String message) {
        super(message);
    }

    public NotSavedSubEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotSavedSubEntityException(Throwable cause) {
        super(cause);
    }

    protected NotSavedSubEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
