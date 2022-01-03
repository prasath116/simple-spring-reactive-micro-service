package com.prs.services.department.client;

import java.util.Map;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Component
@Qualifier("externalClient")
public class ExternalClient {
	private WebClient client;

	public <T> Mono<T> getForMono(String uri, Map<String, String> httpHeaders, Class<T> resClazz) {
		return getClientResponse(uri, httpHeaders)
				.flatMap(r -> r.bodyToMono(resClazz));
	}

	public <T> Flux<T> getForFlux(String uri, Map<String, String> httpHeaders, Class<T> resClazz) {
		return getClientResponse(uri, httpHeaders)
				.flatMapMany(r -> r.bodyToFlux(resClazz));

	}

	public <T, U> Mono<U> postForMono(String uri, Map<String, String> httpHeaders, Class<U> resClazz,
			T reqBody, Class<T> reqClazz) {
		return postForResponse(uri, httpHeaders, reqBody, reqClazz)
				.flatMap(r -> r.bodyToMono(resClazz));
	}

	public <T, U> Flux<U> postForFlux(String uri, Map<String, String> httpHeaders, T reqBody, Class<T> reqClazz,
			Class<U> resClazz) {
		return postForResponse(uri, httpHeaders, reqBody, reqClazz)
				.flatMapMany(r -> r.bodyToFlux(resClazz));

	}
	
	private <T> Mono<ClientResponse> postForResponse(String uri, Map<String, String> httpHeaders, T reqBody,
			Class<T> reqClazz) {
		return client.post()
				.uri(uri)
				.headers(headerConsumer(httpHeaders))
				.body(Mono.just(reqBody), reqClazz)
				.exchange();
	}
	
	private Mono<ClientResponse> getClientResponse(String uri, Map<String, String> httpHeaders) {
		return client.get()
				.uri(uri)
				.headers(headerConsumer(httpHeaders))
				.exchange();
	}
	
	private Consumer<HttpHeaders> headerConsumer(Map<String, String> httpHeaders) {
		return h -> httpHeaders.forEach((k, v) -> h.add(k, v));
	}
}
