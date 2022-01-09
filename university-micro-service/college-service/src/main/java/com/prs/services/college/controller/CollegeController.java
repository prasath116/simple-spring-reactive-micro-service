package com.prs.services.college.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

	private static final Logger LOGGER = LogManager.getLogger(CollegeController.class);
	
	@Autowired
	private ICollegeService service;
	@Autowired
	private DepartmentClient departmentClient;
	@Autowired
	private EmployeeClient employeeClient;
	
	@PostMapping("/add")
	public College add(@RequestBody CollegeEntity college) {
		LOGGER.info("College add: {}", college);
		return service.save(college);
	}
	
	@GetMapping("/findAll")
	public List<College> findAll() {
		LOGGER.info("College find");
		return service.findAll();
	}
	
	@GetMapping("/get-by/{id}")
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
