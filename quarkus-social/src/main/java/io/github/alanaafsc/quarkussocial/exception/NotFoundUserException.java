package io.github.alanaafsc.quarkussocial.exception;

public class NotFoundUserException extends RuntimeException {
    private String message;

    public NotFoundUserException(String message) {
        super(message);
        this.message = message;
    }

    public NotFoundUserException() {
    }
}
