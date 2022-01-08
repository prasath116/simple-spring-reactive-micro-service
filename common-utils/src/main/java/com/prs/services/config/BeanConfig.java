package com.prs.services.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class BeanConfig {
	@Bean
	public ObjectMapper mapper() {
		System.err.println("ObjectMapper bean initiated");
	  return new ObjectMapper();
	}
}
