package br.movely.movelyapp.exceptions;

public class NotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Resource not found";

    public NotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public NotFoundException(String message) {
        super(message != null ? message : DEFAULT_MESSAGE);
    }

    public NotFoundException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message != null ? message : DEFAULT_MESSAGE, cause);
    }
}
