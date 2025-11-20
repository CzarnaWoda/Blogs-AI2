package me.blackwater.blogsai2.domain.exception;


import me.blackwater.blogsai2.api.stereotype.ApiException;
import org.springframework.http.HttpStatus;

@ApiException(status = HttpStatus.BAD_REQUEST, reason = "The requirements for this action have not been met")
public class ObjectRequirementsException extends RuntimeException {
    public ObjectRequirementsException(String message) {
        super(message);
    }
}
