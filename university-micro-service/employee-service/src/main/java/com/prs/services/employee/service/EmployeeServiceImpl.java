package com.prs.services.employee.service;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prs.services.employee.entity.EmployeeEntity;
import com.prs.services.employee.model.Employee;
import com.prs.services.employee.repository.EmployeeRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

	@Autowired
	private EmployeeRepository repository;

	@Override
	public Mono<Employee> save(EmployeeEntity department) {
		EmployeeEntity c = repository.save(department);
		return Mono.just(Optional.of(c).map(mapper).orElse(null));
	}

	@Override
	public Flux<Employee> findAll() {
		return Flux.fromIterable(repository.findAll()).map(mapper);
	}

	@Override
	public Mono<Employee> findById(Long id) {
		return Mono.just(repository.findById(id).map(mapper).orElse(null));
	}

	@Override
	public Flux<Employee> findByCollege(Long collegeId) {
//		return findAll().filter(a -> a.getCollegeId().equals(collegeId));
		return Flux.fromIterable(repository.findByCollegeId(collegeId)).map(mapper);
	}

	@Override
	public Flux<Employee> findByDepartment(Long departmentId) {
//		return findAll().filter(a -> a.getDepartmentId().equals(departmentId));
		return Flux.fromIterable(repository.findByDepartmentId(departmentId)).map(mapper);
	}

	private Function<EmployeeEntity, Employee> mapper = c -> {
		Employee response = new Employee();
		BeanUtils.copyProperties(c, response);
		return response;
	};

}
