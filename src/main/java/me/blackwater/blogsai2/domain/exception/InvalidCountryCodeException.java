package me.blackwater.blogsai2.domain.exception;


import me.blackwater.blogsai2.api.stereotype.ApiException;
import org.springframework.http.HttpStatus;

@ApiException(status = HttpStatus.BAD_REQUEST, reason = "Country code is not correct")
public class InvalidCountryCodeException extends RuntimeException {
    public InvalidCountryCodeException(String message) {
        super(message);
    }
}
