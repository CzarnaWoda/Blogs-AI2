package me.blackwater.blogsai2.domain.exception;

public class IllegalAccountAccessException extends RuntimeException {
    public IllegalAccountAccessException(String message) {
        super(message);
    }
}
