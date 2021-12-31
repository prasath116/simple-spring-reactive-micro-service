package com.prs.services.college.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.prs.services.college.client.DepartmentClient;
import com.prs.services.college.client.EmployeeClient;
import com.prs.services.college.entity.CollegeEntity;
import com.prs.services.college.model.College;
import com.prs.services.college.service.ICollegeService;

@RestController
public class CollegeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CollegeController.class);
	
	@Autowired
	ICollegeService service;
	@Autowired
	DepartmentClient departmentClient;
	@Autowired
	EmployeeClient employeeClient;
	
	@PostMapping
	public College add(@RequestBody CollegeEntity college) {
		LOGGER.info("College add: {}", college);
		return service.save(college);
	}
	
	@GetMapping
	public List<College> findAll() {
		LOGGER.info("College find");
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public College findById(@PathVariable("id") Long id) {
		LOGGER.info("College find: id={}", id);
		return service.findById(id);
	}

	@GetMapping("/{id}/with-departments")
	public College findByIdWithDepartments(@PathVariable("id") Long id) {
		LOGGER.info("College find: id={}", id);
		College college = service.findById(id);
		college.setDepartments(departmentClient.findByCollege(college.getId()));
		return college;
	}
	
	@GetMapping("/{id}/with-departments-and-employees")
	public College findByIdWithDepartmentsAndEmployees(@PathVariable("id") Long id) {
		LOGGER.info("College find: id={}", id);
		College college = service.findById(id);
		college.setDepartments(departmentClient.findByCollegeWithEmployees(college.getId()));
		return college;
	}
	
	@GetMapping("/{id}/with-employees")
	public College findByIdWithEmployees(@PathVariable("id") Long id) {
		LOGGER.info("College find: id={}", id);
		College college = service.findById(id);
		college.setEmployees(employeeClient.findByCollege(college.getId()));
		return college;
	}
	
}
