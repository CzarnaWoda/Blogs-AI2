package me.blackwater.blogsai2.api.aspect;

import me.blackwater.blogsai2.api.handler.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class HandlerLoggingAspect {

    @Around("execution(* execute(..)) && this(me.blackwater.blogsai2.api.handler.Handler)")
    public Object logHandlerExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        final Object target = joinPoint.getTarget();
        if (target instanceof Handler && ((Handler) target).trace()) {
            if (target instanceof CreateHandler<?, ?>) {
                ((CreateHandler<?, ?>) target).log();
            }
            if (target instanceof DeleteHandler<?>) {
                ((DeleteHandler<?>) target).log();
            }
            if(target instanceof GetHandler<?,?>){
                ((GetHandler<?,?>) target).log();
            }
            if(target instanceof UpdateHandler<?,?>){
                ((UpdateHandler<?,?>) target).log();
            }
        }
        return joinPoint.proceed();
    }
}
