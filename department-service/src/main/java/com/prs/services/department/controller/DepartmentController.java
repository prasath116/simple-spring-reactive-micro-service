package com.prs.services.department.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.prs.services.department.client.EmployeeReactiveClient;
import com.prs.services.department.model.Department;
import com.prs.services.department.model.Employee;
import com.prs.services.department.repository.DepartmentRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class DepartmentController {

	private static final Logger LOGGER = LogManager.getLogger(DepartmentController.class);
	
	@Autowired
	private DepartmentRepository repository;
	
	@Autowired
	private EmployeeReactiveClient employeeReactiveClient;
	
	@PostMapping("/")
	public Mono<Department> add(@RequestBody Department department) {
		LOGGER.info("Department add: {}", department);
		return repository.add(department);
	}
	
	@GetMapping("/{id}")
	public Mono<Department> findById(@PathVariable("id") Long id) {
		LOGGER.info("Department find: id={}", id);
		return repository.findById(id);
	}
	
	@GetMapping("/")
	public Flux<Department> findAll() {
		LOGGER.info("Department find");
		return repository.findAll();
	}
	
	@GetMapping("/college/{collegeId}")
	public Flux<Department> findByCollege(@PathVariable("collegeId") Long collegeId) {
		LOGGER.info("Department find: collegeId={}", collegeId);
		return repository.findByCollege(collegeId);
	}
	
	@GetMapping("/college/{collegeId}/with-employees")
	public Flux<Department> findByCollegeWithEmployees(@PathVariable("collegeId") Long collegeId) {
		LOGGER.info("Department find: collegeId={}", collegeId);
		Flux<Department> departments = repository.findByCollege(collegeId);
		return departments.flatMap(d -> {
			return employeeReactiveClient.findByDepartment(d.getId()).collectList().map(l-> {
				d.setEmployees(l);
				return d;
			});
		});
	}
	
	@PostMapping("/addEmployee")
	public Mono<Employee> addEmployee(@RequestBody Employee employee) {
		return employeeReactiveClient.addEmployee(employee);
	}
	
	
}
