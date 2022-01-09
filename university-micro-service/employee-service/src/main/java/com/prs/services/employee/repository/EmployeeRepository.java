package com.prs.services.employee.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prs.services.employee.entity.EmployeeEntity;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
	Optional<EmployeeEntity> findById(Long id);

	List<EmployeeEntity> findByCollegeId(Long collegeId);

	List<EmployeeEntity> findByDepartmentId(Long departmentId);
}
