package me.blackwater.blogsai2.domain.exception;


import me.blackwater.blogsai2.api.stereotype.ApiException;
import org.springframework.http.HttpStatus;

@ApiException(status = HttpStatus.BAD_REQUEST, reason = "Phone number is not correct")
public class InvalidPhoneNumberException extends RuntimeException {
    public InvalidPhoneNumberException(String message) {
        super(message);
    }
}
