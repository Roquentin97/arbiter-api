package com.roquentin.arbiter.services;

import static org.mockito.Mockito.doReturn;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.roquentin.arbiter.dto.ConventionDTO;
import com.roquentin.arbiter.models.Convention;
import com.roquentin.arbiter.models.Cooperation;
import com.roquentin.arbiter.models.User;
import com.roquentin.arbiter.repositories.ConventionRepository;
import com.roquentin.arbiter.repositories.CooperationRepository;

@SpringBootTest
public class ConventionServiceTest {
	
	@Autowired
	private ConventionService service;
	
	
	private static Cooperation cooperation;
	
	private static Convention fullConvention;
	
	private static User user1;
	
	@BeforeAll
	static void init() {
		
		
		cooperation = new Cooperation();
		cooperation.setId(1l);
		cooperation.setName("First cooperation");
		cooperation.setUsers(Set.of(user1));
		cooperation.setPassword("12345678");
		
		fullConvention = new Convention();
		fullConvention.setId(1l);
		fullConvention.setName("ConventionName");
		fullConvention.setDescription("Convention Description");
		fullConvention.setConsequence("Convention Consequence");
		fullConvention.setCooperation(cooperation);
		
	}
	
	
	@MockBean
	private ConventionRepository repository;
	
	@MockBean
	private CooperationRepository coopRepository;
	
	@MockBean
	private CooperationService coopService;
	
	@MockBean
	private UserService userService;
	
	
	@Test
	@DisplayName("Create convention using all valid parameters")
	void createConventionWithAllValidParameters() {
		ConventionDTO conventionDTO = new ConventionDTO();
		
		doReturn(fullConvention).when(repository).saveUsingDTO(any(), any(), any(), any());
		doReturn(true).when(coopService).canUserChangeCooperation((Long)any(), any());
		Convention returnedConvention = service.createConvention(conventionDTO);
		
		Assertions.assertNotNull(returnedConvention, () -> "The saved convention should not be null");
		Assertions.assertNotNull(returnedConvention.getId());
		Assertions.assertEquals(cooperation, returnedConvention.getCooperation(), () -> "cooperation doesn't match");
	}
}
