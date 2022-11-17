package io.github.alanaafsc.quarkussocial.exception;

public class UserEqualsFollowerException extends RuntimeException {
    private String message;

    public UserEqualsFollowerException(String message) {
        super(message);
        this.message = message;
    }

    public UserEqualsFollowerException() {
    }
}
