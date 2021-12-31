package com.prs.services.college.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prs.services.college.entity.CollegeEntity;


public interface CollegeRepository extends JpaRepository<CollegeEntity, Long>{
	public Optional<CollegeEntity> findById(Long id);
}
