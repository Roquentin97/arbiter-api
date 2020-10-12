package com.roquentin.arbiter.services;

import static org.mockito.Mockito.doReturn;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.roquentin.arbiter.models.Convention;
import com.roquentin.arbiter.models.Cooperation;
import com.roquentin.arbiter.repositories.ConventionRepository;

@SpringBootTest
public class ConventionServiceTest {
	
	@Autowired
	private ConventionService service;
	
	
	@MockBean
	private ConventionRepository repository;
	
	@Test
	@DisplayName("Create convention using all valid parameters")
	void createConventionWithAllValidParameters() {
		Convention convention = new Convention();
		convention.setId(1l);
		convention.setName("ConventionName");
		convention.setDescription("Convention Description");
		convention.setConsequence("Convention Consequence");
		Cooperation cooperation = new Cooperation();
		cooperation.setId(1l);
		convention.setCooperation(cooperation);
		
		doReturn(convention).when(repository).save(any());
		
		Convention returnedConvention = service.createConvention(convention);
		
		Assertions.assertNotNull(returnedConvention, () -> "THe saved convention should not be null");
		Assertions.assertNotNull(returnedConvention.getId());
	}
}
