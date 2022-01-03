package com.prs.services.department.aop;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prs.services.department.aop.ExternalLog.ExternalLogBuilder;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Aspect
@Component
public class ExternalLoggingAspect {
    private static final Logger LOGGER = LogManager.getLogger(ExternalLoggingAspect.class);
    
    @Autowired
	private ObjectMapper mapper;
    @Around(value = "execution(public * com.prs.services.department.client.ExternalClient.*(..))")  
    public Object externalLog(ProceedingJoinPoint joinPoint) throws Throwable {
    	final StringBuilder sb = new StringBuilder();
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
			sb.append(end).append("ms taken for request - uri :").append(uri)
			.append("headers : ").append(headers);
			if(argList.size() > 3) 
			{
				sb.append(" body : ").append(argList.get(3));
				builder.requestBody(argList.get(3));
			}
			sb.append(" response ");
			
    		if(res instanceof Flux) {
    			respMono = ((Flux<?>) res).collectList().map(o-> {
    				builder.responseBody(o);
    				return getString(o);
    			});
    		} else if(res instanceof Mono) {
    			respMono = ((Mono<?>) res).map(o-> {
    				builder.responseBody(o);
    				return getString(o);
    			});
    		} else if(res instanceof String) {
    			builder.responseBody(res);
    			respMono = Mono.just(res.toString());
    		} else {
    			builder.responseBody(res);
    			respMono = Mono.just(getString(res));
    		}
    		return res;
		} catch (Exception e) {
			
		} finally {
			respMono.subscribe(s -> {
				sb.append(s);
				log(sb.toString());
				log(getString(builder.build()));
			});
			
		}
		return null;
    }

	private String getString(Object l) {
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
