package ru.toroschin.spring.market.utils;

import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@AllArgsConstructor
public class LoggingAspect {
    private final TimeKeeper timeKeeper;

    @Around("execution(public * ru.toroschin.spring.market.controllers.CartController.*(..))")
    public Object methodCartProfiling(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return methodProfiling(proceedingJoinPoint);
    }

    @Around("execution(public * ru.toroschin.spring.market.controllers.ProductsController.*(..))")
    public Object methodProductsProfiling(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return methodProfiling(proceedingJoinPoint);
    }

    @Around("execution(public * ru.toroschin.spring.market.controllers.OrderController.*(..))")
    public Object methodOrderProfiling(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return methodProfiling(proceedingJoinPoint);
    }

    @Around("execution(public * ru.toroschin.spring.market.controllers.CategoryController.*(..))")
    public Object methodCategoryProfiling(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return methodProfiling(proceedingJoinPoint);
    }

    @Around("execution(public * ru.toroschin.spring.market.controllers.UserController.*(..))")
    public Object methodUserProfiling(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return methodProfiling(proceedingJoinPoint);
    }

    @Around("execution(public * ru.toroschin.spring.market.controllers.AuthController.*(..))")
    public Object methodAuthProfiling(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        return methodProfiling(proceedingJoinPoint);
    }

    private Object methodProfiling(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String key = proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName();
//        System.out.println("start profiling " + key);
        long begin = System.currentTimeMillis();
        Object out = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();
        long duration = end - begin;
        timeKeeper.addTime(key, duration);
//        System.out.println("end profiling " + key);
        return out;
    }

}
