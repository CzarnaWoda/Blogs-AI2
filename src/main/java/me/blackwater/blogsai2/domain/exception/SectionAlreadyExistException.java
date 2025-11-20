package me.blackwater.blogsai2.domain.exception;


import me.blackwater.blogsai2.api.stereotype.ApiException;
import org.springframework.http.HttpStatus;

@ApiException(status = HttpStatus.BAD_REQUEST, reason = "Section already exist!")
public class SectionAlreadyExistException extends RuntimeException {
    public SectionAlreadyExistException(String message) {
        super(message);
    }
}
