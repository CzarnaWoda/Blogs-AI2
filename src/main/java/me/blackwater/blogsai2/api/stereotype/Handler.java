package me.blackwater.blogsai2.api.stereotype;


import me.blackwater.blogsai2.api.enums.HandlerType;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Handler {

    String name() default "Handler";

    boolean transactional() default false;

    HandlerType handlerType() default HandlerType.DEFAULT;
}
