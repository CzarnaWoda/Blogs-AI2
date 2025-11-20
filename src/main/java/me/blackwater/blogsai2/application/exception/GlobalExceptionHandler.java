package me.blackwater.blogsai2.application.exception;

import me.blackwater.blogsai2.api.data.HttpResponse;
import me.blackwater.blogsai2.api.stereotype.ApiException;
import me.blackwater.blogsai2.api.util.TimeUtil;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
class GlobalExceptionHandler {


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<HttpResponse> handle(RuntimeException e) {
        ApiException annotation = e.getClass().getAnnotation(ApiException.class);

        if(annotation != null){
            return ResponseEntity.status(annotation.status()).body(HttpResponse.builder()
                            .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                            .reason(annotation.reason())
                            .message(e.getMessage())
                            .httpStatus(annotation.status())
                            .statusCode(annotation.status().value())
                    .build());
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(HttpResponse.builder()
                        .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                        .reason("Unknown reason for internal server error. Try again later.")
                        .message(e.getMessage())
                        .httpStatus(INTERNAL_SERVER_ERROR)
                        .statusCode(INTERNAL_SERVER_ERROR.value())
                        .build());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HttpResponse> handle(MethodArgumentNotValidException e) {
        return ResponseEntity.status(BAD_REQUEST).body(HttpResponse.builder()
                .timeStamp(TimeUtil.getCurrentTimeWithFormat())
                .reason("Field: " + Objects.requireNonNull(e.getBindingResult().getFieldError()).getField() + " is not correct")
                .message(Objects.requireNonNull(e.getBindingResult().getFieldError()).getField().toUpperCase() + " " +Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage())
                .httpStatus(BAD_REQUEST)
                .statusCode(BAD_REQUEST.value())
                .build());
    }
}
