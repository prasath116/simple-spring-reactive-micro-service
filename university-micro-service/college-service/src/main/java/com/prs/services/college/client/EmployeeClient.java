package com.prs.services.college.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.prs.services.college.model.Employee;

@FeignClient(name = "employee-service")
public interface EmployeeClient {

	@GetMapping("/college/{collegeId}")
	List<Employee> findByCollege(@PathVariable("collegeId") Long collegeId);
	
}
