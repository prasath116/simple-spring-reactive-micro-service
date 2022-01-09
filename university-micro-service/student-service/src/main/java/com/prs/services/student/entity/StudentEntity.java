package com.prs.services.student.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Data
@Entity(name = "Student")
@Table(name = "Student")
@ToString
public class StudentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private int age;

	@Column(name = "college_id")
	private Long collegeId;
	@Column(name = "department_id")
	private Long departmentId;
}
