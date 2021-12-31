package com.prs.services.department.model;

import java.util.ArrayList;
import java.util.List;

public class Department {

	private Long id;
	private Long collegeId;
	private String name;
	private List<Employee> employees = new ArrayList<>();

	public Department() {
		
	}

	public Department(Long collegeId, String name) {
		super();
		this.collegeId = collegeId;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(Long collegeId) {
		this.collegeId = collegeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", collegeId=" + collegeId + ", name=" + name + "]";
	}

}
