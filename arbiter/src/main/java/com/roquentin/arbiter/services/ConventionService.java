package com.roquentin.arbiter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roquentin.arbiter.models.Convention;
import com.roquentin.arbiter.repositories.ConventionRepository;

@Service
public class ConventionService {
	
	@Autowired
	private ConventionRepository repository;
	
	public Convention createConvention(Convention newConvention) {
		//TODO:: voting
		return repository.save(newConvention);
	}
	
	public void deleteConvention(Long id) {
		//TODO: voting
		repository.deleteById(id);
	}

}
