package com.prs.services.student.service;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prs.services.student.entity.StudentEntity;
import com.prs.services.student.model.Student;
import com.prs.services.student.repository.StudentRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StudentServiceImpl implements IStudentService {

	@Autowired
	private StudentRepository repository;

	@Override
	public Mono<Student> save(StudentEntity department) {
		StudentEntity c = repository.save(department);
		return Mono.just(Optional.of(c).map(mapper).orElse(null));
	}

	@Override
	public Flux<Student> findAll() {
		return Flux.fromIterable(repository.findAll()).map(mapper);
	}

	@Override
	public Mono<Student> findById(Long id) {
		return Mono.just(repository.findById(id).map(mapper).orElse(null));
	}

	@Override
	public Flux<Student> findByCollege(Long collegeId) {
//		return findAll().filter(a -> a.getCollegeId().equals(collegeId));
		return Flux.fromIterable(repository.findByCollegeId(collegeId)).map(mapper);
	}

	@Override
	public Flux<Student> findByDepartment(Long departmentId) {
//		return findAll().filter(a -> a.getDepartmentId().equals(departmentId));
		return Flux.fromIterable(repository.findByDepartmentId(departmentId)).map(mapper);
	}

	private Function<StudentEntity, Student> mapper = c -> {
		Student response = new Student();
		BeanUtils.copyProperties(c, response);
		return response;
	};

}
