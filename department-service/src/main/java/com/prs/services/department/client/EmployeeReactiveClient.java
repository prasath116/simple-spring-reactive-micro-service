package com.prs.services.department.client;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.prs.services.department.model.Employee;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class EmployeeReactiveClient {

	/*@Autowired
	@Qualifier("employeeWebClient")
	private WebClient employeeWebClient;*/

	@Autowired
	@Qualifier("externalClient")
	private ExternalClient externalClient;

	public Flux<Employee> findByDepartment(Long departmentId) {
		return externalClient.getForFlux("/department/" + departmentId, getDefaultHeaders(), Employee.class);
	}

	public Mono<Employee> addEmployee(Employee employee) {
		return externalClient.postForMono("/", getDefaultHeaders(), Employee.class, employee, Employee.class);

	}

	private Map<String, String> getDefaultHeaders() {
		Map<String, String> h = new HashMap<>();
		h.put(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
		return h;
	}

}
