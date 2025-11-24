package me.blackwater.blogsai2.domain.exception;

import me.blackwater.blogsai2.api.stereotype.ApiException;
import org.springframework.http.HttpStatus;

@ApiException(reason = "Article has not been found", status = HttpStatus.BAD_REQUEST)
public class ArticleNotFoundException extends RuntimeException {
    public ArticleNotFoundException(String message) {
        super(message);
    }
}
