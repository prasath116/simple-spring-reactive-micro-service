package com.prs.services.department.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prs.services.department.entity.DepartmentEntity;

public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Long> {
	Optional<DepartmentEntity> findById(Long id);
	List<DepartmentEntity> findByCollegeId(Long collegeId);
}
