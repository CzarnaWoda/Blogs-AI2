package me.blackwater.blogsai2.api.stereotype;

import org.springframework.http.HttpStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ApiException {
    HttpStatus status() default HttpStatus.BAD_REQUEST;
    String reason() default "";
}
