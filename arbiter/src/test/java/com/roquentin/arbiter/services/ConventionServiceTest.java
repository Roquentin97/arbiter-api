package com.roquentin.arbiter.services;

import static org.mockito.Mockito.doReturn;

import java.util.Set;

import static org.junit.Assert.assertTrue;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.roquentin.arbiter.dto.ConventionDTO;
import com.roquentin.arbiter.expections.UnauthorizedException;
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
		
		user1 = new User();
		user1.setId(1l);
		user1.setName("user1");
		user1.setEmail("user1@gmail");
		
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
	@DisplayName("Convention creation by an authorized user")
	void testCreateConventionWithAuthorizedUser() {
		ConventionDTO conventionDTO = new ConventionDTO();
		
		doReturn(1).when(repository).saveUsingDTO(any(), any(), any(), any());
		doReturn(true).when(coopService).canUserChangeCooperation((Long)any(), any());
		
		assertTrue(service.createConvention(conventionDTO));
		
	}
	
	@Test
	@DisplayName("Convention creation by an unauthorized user")
	void testCreateConventionWithUnauthorizedUser() {
		ConventionDTO conventionDTO = new ConventionDTO();
		
		doReturn(1).when(repository).saveUsingDTO(any(), any(), any(), any());
		doReturn(false).when(coopService).canUserChangeCooperation((Long)any(), any());
		
		assertThrows(UnauthorizedException.class, () -> service.createConvention(conventionDTO));
		
	}
	
	@Test
	@DisplayName("Convention deletion by an authorized user")
	void testDeleteConventionWithAuthorizedUser() {
		doReturn(true).when(coopService).canUserChangeCooperation((Cooperation)any(), any());
	}
	
	@Test
	@DisplayName("Convention deletion by an unauthorized user")
	void testDeleteConventionWithUnauthorizedUser() {
		doReturn(false).when(coopService).canUserChangeCooperation((Cooperation)any(), any());
		doReturn(new Convention()).when(repository).getOne(any());
		
		assertThrows(UnauthorizedException.class, () -> service.deleteConvention(any()));
	}
	
	
}
