package com.prs.services.college.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prs.services.college.entity.CollegeEntity;
import com.prs.services.college.model.College;
import com.prs.services.college.repository.CollegeRepository;

@Service
public class CollegeServiceImpl implements ICollegeService {
	@Autowired
	private CollegeRepository repository;

	@Override
	public College save(CollegeEntity college) {
		CollegeEntity c = repository.save(college);
		return Optional.of(c).map(mapper).orElse(null);
	}

	@Override
	public List<College> findAll() {
		return repository.findAll().stream().map(mapper).collect(Collectors.toList());
	}

	@Override
	public College findById(Long id) {
		return repository.findById(id).map(mapper).orElse(null);
	}

	private Function<? super CollegeEntity, ? extends College> mapper = c -> {
		College response = new College();
		BeanUtils.copyProperties(c, response);
		return response;
	};
}
