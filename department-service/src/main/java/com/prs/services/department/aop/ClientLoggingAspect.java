package com.prs.services.department.aop;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class ClientLoggingAspect {
	private static final Logger LOGGER = LogManager.getLogger(ClientLoggingAspect.class);
    
    @Before(value = "execution(* com.prs.services.department.controller.*.*(..))")  
    public void logControllerAccess(JoinPoint joinPoint) {
    	Arrays.asList(joinPoint.getArgs()).forEach(arg-> {
    		LOGGER.info("Arg "+arg);
    	});
    	LOGGER.info("Executing {} request"+joinPoint.getArgs());
    }
    
    @Around(value = "execution(* com.prs.services.department.controller.*.*(..))")  
    public Object logControllerAccess(ProceedingJoinPoint joinPoint) throws Throwable {
        LOGGER.info("Executing {} request"+joinPoint.getArgs());
        return joinPoint.proceed();
    }
    
}
