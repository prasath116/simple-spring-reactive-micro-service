package com.prs.services.department.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Employee {

	private Long id;
	private Long collegeId;
	private Long departmentId;
	private String name;
	private int age;
	private String position;

	public Employee() {
	}

	public Employee(String name, int age, String position) {
		this.name = name;
		this.age = age;
		this.position = position;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", position=" + position + "]";
	}

}
