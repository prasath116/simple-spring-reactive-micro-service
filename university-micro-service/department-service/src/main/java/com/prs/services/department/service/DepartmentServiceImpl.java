package com.prs.services.department.service;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prs.services.department.entity.DepartmentEntity;
import com.prs.services.department.model.Department;
import com.prs.services.department.repository.DepartmentRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DepartmentServiceImpl implements IDepartmentService {
	@Autowired
	private DepartmentRepository repository;

	@Override
	public Mono<Department> save(DepartmentEntity department) {
		DepartmentEntity c = repository.save(department);
		return Mono.just(Optional.of(c).map(mapper).orElse(null));
	}

	@Override
	public Flux<Department> findAll() {
		return Flux.fromIterable(repository.findAll()).map(mapper);
	}

	@Override
	public Mono<Department> findById(Long id) {
		return Mono.just(repository.findById(id).map(mapper).orElse(null));
	}
	
	@Override
	public Flux<Department> findByCollege(Long collegeId) {
//		return findAll().filter(a -> a.getCollegeId().equals(collegeId));
		return Flux.fromIterable(repository.findByCollegeId(collegeId)).map(mapper);
	}

	private Function<DepartmentEntity, Department> mapper = c -> {
		Department response = new Department();
		BeanUtils.copyProperties(c, response);
		return response;
	};
	
}
