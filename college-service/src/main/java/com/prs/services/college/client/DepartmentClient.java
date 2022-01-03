package com.prs.services.college.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.prs.services.college.model.Department;

@FeignClient(name = "department-service")
public interface DepartmentClient {

	@GetMapping("/college/{collegeId}")
	public List<Department> findByCollege(@PathVariable("collegeId") Long collegeId);
	
	@GetMapping("/college/{collegeId}/with-employees")
	public List<Department> findByCollegeWithEmployees(@PathVariable("collegeId") Long collegeId);
	
}
