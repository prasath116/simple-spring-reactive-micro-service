package com.prs.services.student.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prs.services.student.entity.StudentEntity;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
	Optional<StudentEntity> findById(Long id);

	List<StudentEntity> findByCollegeId(Long collegeId);

	List<StudentEntity> findByDepartmentId(Long departmentId);
}
