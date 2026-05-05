package br.movely.movelyapp.exceptions;

public class ForbiddenException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Forbidden";

    public ForbiddenException() {
        super(DEFAULT_MESSAGE);
    }

    public ForbiddenException(String message) {
        super(message != null ? message : DEFAULT_MESSAGE);
    }

    public ForbiddenException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message != null ? message : DEFAULT_MESSAGE, cause);
    }
}
