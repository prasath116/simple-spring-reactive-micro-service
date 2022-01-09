package com.prs.services.aop;

import java.util.Map;
import java.util.Optional;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExternalLog {
	private String uri;
	private Map<String, String> headers;
	private Object requestBody;
	private Object responseBody;
	private long timeTaken;

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(timeTaken).append(" ms taken for request - uri :").append(uri).append(" headers : ").append(headers);
		Optional.ofNullable(requestBody).ifPresent(body -> sb.append(" body : ").append(body));
		sb.append(" response ").append(responseBody);
		return sb.toString();
	}
}
