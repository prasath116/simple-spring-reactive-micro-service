package com.prs.services.college.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class College {

	private Long id;
	private String name;
	private String address;
	private List<Department> departments = new ArrayList<>();
	private List<Employee> employees = new ArrayList<>();

	@Override
	public String toString() {
		return "College [id=" + id + ", name=" + name + ", address=" + address + "]";
	}

}
