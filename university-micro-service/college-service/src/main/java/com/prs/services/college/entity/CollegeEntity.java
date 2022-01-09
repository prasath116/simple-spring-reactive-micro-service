package com.prs.services.college.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity(name = "College")
@Table(name = "College")
public class CollegeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String address;

	@Override
	public String toString() {
		return "College [id=" + id + ", name=" + name + ", address=" + address + "]";
	}

}
