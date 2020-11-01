package com.roquentin.arbiter.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roquentin.arbiter.dto.ConventionDTO;
import com.roquentin.arbiter.exceptions.UnauthorizedException;
import com.roquentin.arbiter.models.Convention;
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
	public boolean createConvention(ConventionDTO newConvention) {
		//TODO:: voting
		if (!cooperationService.canUserChangeCooperation(newConvention.getCooperationId(), userService.getCurrentUser()))
			throw new UnauthorizedException("No permissions to change the cooperation");
		
		return 0 < repository.saveUsingDTO(newConvention.getName(), newConvention.getDescription(),
				newConvention.getCooperationId(), newConvention.getConsequence());
		
	}
	
	public void deleteConvention(Long id) {
		Convention convention = repository.getOne(id);
		if (!cooperationService.canUserChangeCooperation(convention.getCooperation(), userService.getCurrentUser()))
			throw new UnauthorizedException("No permissions to change the cooperation");
		
		//TODO: voting
		repository.deleteById(id);
	}
	
	

}
