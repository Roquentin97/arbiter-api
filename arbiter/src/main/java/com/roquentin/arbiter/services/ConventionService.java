package com.roquentin.arbiter.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roquentin.arbiter.dto.ConventionDTO;
import com.roquentin.arbiter.expections.UnauthorizedException;
import com.roquentin.arbiter.models.Convention;
import com.roquentin.arbiter.models.Cooperation;
import com.roquentin.arbiter.repositories.ConventionRepository;

@Service
public class ConventionService {
	
	@Autowired
	private ConventionRepository repository;
	
	@Autowired
	private CooperationService cooperationService;
	
	@Autowired
	private UserService userService;
	
	@Transactional
	public Convention createConvention(ConventionDTO newConvention) {
		//TODO:: voting
		if (!cooperationService.canUserChangeCooperation(newConvention.getCooperationId(), userService.getCurrentUser()))
			throw new UnauthorizedException("No permissions to change the cooperation");
		
		return repository.saveUsingDTO(newConvention.getName(), newConvention.getDescription(),
				newConvention.getCooperationId(), newConvention.getConsequence());
		
	}
	
	public void deleteConvention(Long id) {
		//TODO: voting
		repository.deleteById(id);
	}
	
	

}
