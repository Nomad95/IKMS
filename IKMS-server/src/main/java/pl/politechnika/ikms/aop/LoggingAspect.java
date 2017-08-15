package pl.politechnika.ikms.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class LoggingAspect {

    @Pointcut(
            "execution(* pl.politechnika.ikms.repository..*(..)) ||" +
            "execution(* pl.politechnika.ikms.service..*(..)) ||" +
            "execution(* pl.politechnika.ikms.commons..*(..)) ||"+
            "execution(* pl.politechnika.ikms.rest..*(..)) ||"+
            "execution(* pl.politechnika.ikms.domain..*(..)) ||"+
            "execution(* pl.politechnika.ikms.exceptions..*(..)) ||"+
            "execution(* pl.politechnika.ikms.commons..*(..))")
    public void methodsAffected(){}

    @Before("methodsAffected()")
    public void logBefore(JoinPoint joinPoint){
        log.info(joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName()+" - START");
    }

    @After("methodsAffected()")
    public void logAfter(JoinPoint joinPoint){
        log.info(joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName()+" - END");
    }

    @AfterReturning(pointcut = "methodsAffected()", returning = "returnedValue")
    public void logAfterReturning(JoinPoint joinPoint, Object returnedValue){
        log.info(joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName()+" - RETURNS:");
        log.info("{}",returnedValue);
    }

    @AfterThrowing(pointcut = "methodsAffected()", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable exception){
        log.error(
                joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName()+" - THREW: "
                + exception);
    }
}
