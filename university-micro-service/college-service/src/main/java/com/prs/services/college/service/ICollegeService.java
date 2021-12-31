package com.prs.services.college.service;

import java.util.List;

import com.prs.services.college.entity.CollegeEntity;
import com.prs.services.college.model.College;

public interface ICollegeService {

	College save(CollegeEntity college);

	List<College> findAll();

	College findById(Long id);

}
