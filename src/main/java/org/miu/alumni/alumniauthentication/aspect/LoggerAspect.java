package org.miu.alumni.alumniauthentication.aspect;



import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAspect {

    @Pointcut("@annotation(org.miu.alumni.alumniauthentication.aspect.annotation.LogMe)")
    public void logMeAnnotation(){

    }

    @Before(" logMeAnnotation()")
    public void logBefore(JoinPoint joinPoint){
        System.out.println("Log before the method: " + joinPoint.getSignature().getName());
    }
}
