package io.github.alanaafsc.quarkussocial.exception;

public class FollowsException extends RuntimeException {
    private String message;

    public FollowsException(String message) {
        super(message);
        this.message = message;
    }

    public FollowsException() {
    }

}
