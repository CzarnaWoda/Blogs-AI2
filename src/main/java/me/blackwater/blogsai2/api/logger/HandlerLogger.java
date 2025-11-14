package me.blackwater.blogsai2.api.logger;


import lombok.extern.log4j.Log4j2;
import me.blackwater.blogsai2.api.handler.CreateHandler;
import me.blackwater.blogsai2.api.handler.DeleteHandler;
import me.blackwater.blogsai2.api.handler.GetHandler;
import me.blackwater.blogsai2.api.handler.UpdateHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@Log4j2
public class HandlerLogger {


    public static void logHandler(CreateHandler<?, ?> handler) {
        Class<?> handlerClass = handler.getClass();

        Type[] interfaces = handlerClass.getGenericInterfaces();

        for (Type type : interfaces) {
            if (type instanceof ParameterizedType parameterizedType) {

                Type inputType = parameterizedType.getActualTypeArguments()[0];
                Type outputType = parameterizedType.getActualTypeArguments()[1];

                log.info(
                        "Create handler log: {} | input: {} | output: {} | handler: {}",
                        handlerClass.getSimpleName(),
                        inputType.getTypeName(),
                        outputType.getTypeName(),
                        handler
                );
            }
        }
    }

    public static void logHandler(DeleteHandler<?> handler) {
        Class<?> handlerClass = handler.getClass();

        Type[] interfaces = handlerClass.getGenericInterfaces();

        for (Type type : interfaces) {
            if (type instanceof ParameterizedType parameterizedType) {

                Type inputType = parameterizedType.getActualTypeArguments()[0];

                log.info(
                        "Delete handler log: {} | input: {} | handler: {}",
                        handlerClass.getSimpleName(),
                        inputType.getTypeName(),
                        handler
                );
            }
        }
    }
    public static void logHandler(GetHandler<?,?> handler) {
        Class<?> handlerClass = handler.getClass();

        Type[] interfaces = handlerClass.getGenericInterfaces();

        for (Type type : interfaces) {
            if (type instanceof ParameterizedType parameterizedType) {

                Type inputType = parameterizedType.getActualTypeArguments()[1];
                Type outputType = parameterizedType.getActualTypeArguments()[0];

                log.info(
                        "Get handler log: {} | input ID: {} | get value: {} | handler: {}",
                        handlerClass.getSimpleName(),
                        inputType.getTypeName(),
                        outputType.getTypeName(),
                        handler
                );
            }
        }
    }
    public static void logHandler(UpdateHandler<?,?> handler) {
        Class<?> handlerClass = handler.getClass();

        Type[] interfaces = handlerClass.getGenericInterfaces();

        for (Type type : interfaces) {
            if (type instanceof ParameterizedType parameterizedType) {

                Type inputType = parameterizedType.getActualTypeArguments()[0];
                Type outputType = parameterizedType.getActualTypeArguments()[1];

                log.info(
                        "Update handler log: {} | input update DTO: {} | returns: {} | handler: {}",
                        handlerClass.getSimpleName(),
                        inputType.getTypeName(),
                        outputType.getTypeName(),
                        handler
                );
            }
        }
    }


}
