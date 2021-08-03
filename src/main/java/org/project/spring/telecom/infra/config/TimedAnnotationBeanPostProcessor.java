package org.project.spring.telecom.infra.config;

import lombok.extern.log4j.Log4j2;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.project.spring.telecom.infra.annotation.Timed;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Log4j2
@Component
public class TimedAnnotationBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = bean.getClass().getMethods();
        for (Method method : methods) {
            Timed annotation = method.getAnnotation(Timed.class);
            if (annotation != null) {
                return proxiedBean(bean);
            }

        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }

    private Object proxiedBean(Object bean) {
        ProxyFactory proxyFactory = new ProxyFactory(bean);
        proxyFactory.addAdvice(new LoggingInterceptor());
        return proxyFactory.getProxy();
    }

    private static class LoggingInterceptor implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation methodInvocation) throws Throwable {
            long start = System.currentTimeMillis();
            Object returnValue = methodInvocation.proceed();
            long elapsedTime = System.currentTimeMillis() - start;
            String methodName = methodInvocation.getMethod().getName();
            log.info("Method: '" + methodName + "'  execution time: " + elapsedTime + " milliseconds.");
            return returnValue;
        }
    }
}
