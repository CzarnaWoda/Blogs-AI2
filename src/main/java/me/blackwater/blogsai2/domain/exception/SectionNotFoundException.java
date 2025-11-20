package me.blackwater.blogsai2.domain.exception;

import me.blackwater.blogsai2.api.stereotype.ApiException;
import org.springframework.http.HttpStatus;

@ApiException(reason = "Section has not been found", status = HttpStatus.BAD_REQUEST)
public class SectionNotFoundException extends RuntimeException {
    public SectionNotFoundException(String message) {
        super(message);
    }
}
