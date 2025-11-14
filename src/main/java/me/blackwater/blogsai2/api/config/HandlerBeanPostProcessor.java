package me.blackwater.blogsai2.infrastructure.config;

import lombok.RequiredArgsConstructor;
import me.blackwater.blogsai2.api.stereotype.Handler;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.lang.reflect.Method;

@RequiredArgsConstructor
@Component
public class HandlerBeanPostProcessor implements BeanPostProcessor {

    private final PlatformTransactionManager transactionManager;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();

        Handler handler = beanClass.getAnnotation(Handler.class);

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

        // Dodaj transaction interceptor który obsługuje WSZYSTKIE metody
        TransactionInterceptor transactionInterceptor = new TransactionInterceptor(
                transactionManager,
                new MatchAlwaysTransactionAttributeSource()
        );

        proxyFactory.addAdvice(transactionInterceptor);

        return proxyFactory.getProxy();
    }
}
