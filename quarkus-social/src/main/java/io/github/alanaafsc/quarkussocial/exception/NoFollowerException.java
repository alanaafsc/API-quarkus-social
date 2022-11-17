package io.github.alanaafsc.quarkussocial.exception;

public class NoFollowerException extends RuntimeException {
    private String message;

    public NoFollowerException(String message) {
        super(message);
        this.message = message;
    }

    public NoFollowerException() {
    }
}
