package com.prs.services.aop;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prs.services.aop.ExternalLog.ExternalLogBuilder;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Aspect
@Component
public class ExternalLoggingAspect {
    private static final Logger LOGGER = LogManager.getLogger(ExternalLoggingAspect.class);
    
    @Autowired
	private ObjectMapper mapper;
    
    @Pointcut("execution(public * *(..))") //this should work for the public pointcut
    private void anyPublicOperation() {}

    @Pointcut("@within(org.springframework.cloud.openfeign.FeignClient)") //this should work for the annotation service pointcut
    private void inService() {}

    @Around("anyPublicOperation() && inService()")
    public Object externalLogFeign(ProceedingJoinPoint joinPoint) throws Throwable {
    	//Can add log similar to externalLog
    	return joinPoint.proceed();
    }

    @Around(value = "execution(public * com.prs.services.*.client.ExternalClient.*(..))")  
    public Object externalLog(ProceedingJoinPoint joinPoint) throws Throwable {
//    	final StringBuilder sb = new StringBuilder();
    	final ExternalLogBuilder builder = ExternalLog.builder();
    	Mono<String> respMono = null;
    	try {
    		long start = System.currentTimeMillis();
    		Object res = joinPoint.proceed();
    		long end = System.currentTimeMillis() - start;
    		List<Object> argList = Arrays.asList(Optional.of(joinPoint.getArgs()).orElse(new String[] {""}));
    		
			String uri = argList.isEmpty() ? "" : (String) argList.get(0);
			Map<String, String> headers = argList.size() < 2 ? null : (Map<String, String>) argList.get(1);
			builder.headers(headers).uri(uri).timeTaken(end);
//			sb.append(end).append("ms taken for request - uri :").append(uri)
//			.append("headers : ").append(headers);
			if(argList.size() > 3) 
			{
//				sb.append(" body : ").append(argList.get(3));
				builder.requestBody(argList.get(3));
			}
//			sb.append(" response ");
			
    		if(res instanceof Flux) {
    			respMono = ((Flux<?>) res).collectList().map(o-> {
    				return getJsonString(o);
    			});
    		} else if(res instanceof Mono) {
    			respMono = ((Mono<?>) res).map(o-> {
    				return getJsonString(o);
    			});
    		} else if(res instanceof String) {
    			respMono = Mono.just(res.toString());
    		} else {
    			respMono = Mono.just(getJsonString(res));
    		}
    		return res;
		} catch (Exception e) {
			
		} finally {
			respMono.subscribe(s -> {
//				sb.append(s);
//				log(sb.toString());
				builder.responseBody(s);
				log(getJsonString(builder.build()));
				log(builder.build().toString());
			});
			
		}
		return null;
    }

	private String getJsonString(Object l) {
		try {
			return mapper.writeValueAsString(l);
		} catch (JsonProcessingException e) {
			return e.getMessage();
		}
	}

	private void log(String s) {
		LOGGER.info(s);
	}
    
}
