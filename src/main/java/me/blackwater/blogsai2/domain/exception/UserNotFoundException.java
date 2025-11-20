package me.blackwater.blogsai2.domain.exception;

import me.blackwater.blogsai2.api.stereotype.ApiException;
import org.springframework.http.HttpStatus;

@ApiException(reason = "User not found", status = HttpStatus.BAD_REQUEST)
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
