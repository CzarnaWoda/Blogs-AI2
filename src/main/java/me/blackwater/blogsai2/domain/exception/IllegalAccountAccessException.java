package me.blackwater.blogsai2.domain.exception;


import me.blackwater.blogsai2.api.stereotype.ApiException;
import org.springframework.http.HttpStatus;

@ApiException(status = HttpStatus.FORBIDDEN, reason = "Illegal access to account")
public class IllegalAccountAccessException extends RuntimeException {
    public IllegalAccountAccessException(String message) {
        super(message);
    }
}
