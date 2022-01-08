package com.prs.services.aop;

import java.util.Map;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Builder
public class ExternalLog {
	private String uri;
	private Map<String, String> headers;
	private Object requestBody;
	private Object responseBody;
	private long timeTaken;
}
