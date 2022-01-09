package com.prs.services.employee.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Data
@Entity(name = "Employee")
@Table(name = "Employee")
@ToString
public class EmployeeEntity {

	private Long id;
	private Long collegeId;
	private Long departmentId;
	private String name;
	private int age;
	private String position;

}
