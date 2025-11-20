package me.blackwater.blogsai2.domain.exception;


import me.blackwater.blogsai2.api.stereotype.ApiException;
import org.springframework.http.HttpStatus;

@ApiException(status = HttpStatus.BAD_REQUEST, reason = "Invalid credentials!")
public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
