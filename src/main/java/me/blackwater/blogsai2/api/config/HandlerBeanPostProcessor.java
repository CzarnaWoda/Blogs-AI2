package me.blackwater.blogsai2.api.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import me.blackwater.blogsai2.api.handler.CreateHandler;
import me.blackwater.blogsai2.api.handler.DeleteHandler;
import me.blackwater.blogsai2.api.handler.GetHandler;
import me.blackwater.blogsai2.api.handler.UpdateHandler;
import me.blackwater.blogsai2.api.logger.HandlerLogger;
import me.blackwater.blogsai2.api.stereotype.ApiException;
import me.blackwater.blogsai2.api.stereotype.Handler;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

@RequiredArgsConstructor
@Component
@Log4j2
public class HandlerBeanPostProcessor implements BeanPostProcessor {

    private final PlatformTransactionManager transactionManager;
    private int handlerCount = 0;
    private int createHandlerCount = 0;
    private int updateHandlerCount = 0;
    private int deleteHandlerCount = 0;
    private int getHandlerCount = 0;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();

        Handler handler = beanClass.getAnnotation(Handler.class);

        if(handler != null){
            handlerCount++;
            if(bean instanceof CreateHandler<?,?>){
                createHandlerCount++;
            }
            if(bean instanceof UpdateHandler<?,?>){
                updateHandlerCount++;
            }
            if(bean instanceof DeleteHandler<?>){
                deleteHandlerCount++;
            }
            if(bean instanceof GetHandler<?,?>){
                getHandlerCount++;
            }
        }
        if(handler != null && handler.transactional()){
            try {
                beanClass.getMethod("execute", Object.class);

            } catch (NoSuchMethodException e) {
                throw new RuntimeException("Execute method in handler " + beanClass.getSimpleName() +" has not been found");
            }
            return createTransactionalProxy(bean);
        }
        return bean;
    }

    private Object createTransactionalProxy(Object target) {
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true); // CGLIB proxy

        TransactionInterceptor transactionInterceptor = new TransactionInterceptor(
                transactionManager,
                new MatchAlwaysTransactionAttributeSource()
        );

        proxyFactory.addAdvice(transactionInterceptor);

        return proxyFactory.getProxy();
    }
}
