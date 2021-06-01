package ru.toroschin.spring.market.utils;

import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@AllArgsConstructor
public class LoggingAspect {
    private final TimeKeeper timeKeeper;

    @Around("execution(public * ru.toroschin.spring.market.controllers.*.*(..))")
    public Object methodProfiling(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String key = proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName();
        long begin = System.currentTimeMillis();
        Object out = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();
        long duration = end - begin;
        timeKeeper.addTime(key, duration);
        return out;
    }

}
