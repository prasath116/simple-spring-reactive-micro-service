package com.prs.services.department.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.prs.services.department.model.Department;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DepartmentRepository {

	private List<Department> departments = new ArrayList<>();
	
	public Mono<Department> add(Department department) {
		department.setId((long) (departments.size()+1));
		departments.add(department);
		return Mono.just(department);
	}
	
	public Mono<Department> findById(Long id) {
		Optional<Department> department = departments.stream().filter(a -> a.getId().equals(id)).findFirst();
		if (department.isPresent())
			return Mono.just(department.get());
		else
			return Mono.empty();
	}
	
	public Flux<Department> findAll() {
		return Flux.fromStream(departments.stream());
	}
	
	public Flux<Department> findByCollege(Long collegeId) {
		System.err.println("departments : "+departments.size());
		return Flux.fromStream(departments.stream().filter(a -> a.getCollegeId().equals(collegeId)));//.collect(Collectors.toList());
	}
	
}
