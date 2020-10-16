package com.roquentin.arbiter.services;


import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.roquentin.arbiter.dto.CooperationRefDTO;
import com.roquentin.arbiter.expections.CooperationNotFoundException;
import com.roquentin.arbiter.expections.UnauthorizedException;
import com.roquentin.arbiter.models.Cooperation;
import com.roquentin.arbiter.models.User;
import com.roquentin.arbiter.payloads.requests.LoginRequest;
import com.roquentin.arbiter.repositories.CooperationRepository;

@SpringBootTest
public class CooperationServiceTest {
	
	@Autowired
	private CooperationService service;
	
	@MockBean
	private InvitationService invitationService;
	
	@MockBean
	private CooperationRepository repository;
	
	@MockBean 
	private UserService userService;
	
	@MockBean
	private PasswordEncoder encoder;
	
	private static User user1, user2;
	private static Cooperation newCooperation1, createdCooperation1, newCooperation2, createdCooperation2;
	
	@BeforeEach
	private void init() {
		user1 = new User();
		user1.setId(1l);
		user1.setName("user1");
		user1.setEmail("user1@gmail.com");
		user1.setUsername("useruser1");
		
		user2 = new User();
		user2.setId(2l);
		user2.setName("user2");
		user2.setEmail("user2@gmail.com");
		user2.setUsername("useruser2");
		
		newCooperation1 = new Cooperation();
		newCooperation1.setName("first cooperation");
		newCooperation1.setDescription("Description of the first cooperation");
		
		createdCooperation1 = new Cooperation();
		createdCooperation1.setId(1l);
		createdCooperation1.setName(newCooperation1.getName());
		createdCooperation1.setDescription(newCooperation1.getDescription());
		createdCooperation1.setPassword("12345678");
		createdCooperation1.setUsers(new HashSet<User>(Set.of(user1, user2)));
		
		newCooperation2 = new Cooperation();
		newCooperation2.setName("second cooperation");
		newCooperation2.setDescription("Description of the second cooperation");
		
		createdCooperation2 = new Cooperation();
		createdCooperation2.setId(2l);
		createdCooperation2.setName(newCooperation2.getName());
		createdCooperation2.setDescription(newCooperation2.getDescription());
		createdCooperation2.setPassword("12345678");
		createdCooperation2.setUsers(new HashSet<User>(Set.of(user1)));
		
		
	}
	
	@Test
	@DisplayName("Cooperation creation")
	public void testCreateCooperation() {
		
		when(invitationService.createInvitation(any(), any()))
			.thenReturn("0sx000xs0");
		
		when(repository.save(any()))
			.thenReturn(createdCooperation1);
		
		when(userService.getCurrentUser())
			.thenReturn(user1);
		
		when(encoder.encode(any()))
			.thenReturn("12345678");
		
		var resultMap = service.createCooperation(newCooperation1);
		
		assertEquals(Map.of("invitation", "0sx000xs0"), resultMap);
		
	}
	
	@Test
	@DisplayName("List of user`s cooperations")
	public void testGetUsersCooperations() {
		
		var toReturn = Set.of(
				new CooperationRefDTO(
					createdCooperation1.getId(),
					createdCooperation1.getName(),
					createdCooperation1.getDescription()
				),
				new CooperationRefDTO(
					createdCooperation2.getId(),
					createdCooperation2.getName(),
					createdCooperation2.getDescription()
				));
		
		when(repository.findByUsersToRefDTO(any()))
			.thenReturn(toReturn);
		
		assertEquals(toReturn, service.getUsersCooperations());
	}
	
	@Test
	@DisplayName("Authorized User leaves an existing cooperation with more users present")
	public void testLeaveExistingCooperation() {
		
		// user1 must be placed into cooperation`s users within init() method
		assertTrue(createdCooperation1.getUsers().contains(user1));
		
		when(repository.findById(any()))
			.thenReturn(Optional.of(createdCooperation1));
		
		when(userService.getCurrentUser())
			.thenReturn(user1);
		
		service.leaveCooperation(createdCooperation1.getId());
		
		// now current user (user1) must be removed from cooperations users
		assertFalse(createdCooperation1.getUsers().contains(user1));
				
	}
	
	@DisplayName("Authorized User leaves an existing cooperation with no more users")
	public void testLeaveExistingCooperationWithSingleUser() {
		
		//ONLY  user1 must be placed into cooperation`s users within init() method
		assert(createdCooperation2.getUsers().contains(user1));
		assertEquals(1, createdCooperation2.getUsers().size());
		
		when(repository.findById(any()))
			.thenReturn(Optional.of(createdCooperation2));
		
		when(userService.getCurrentUser())
			.thenReturn(user1);
		
		
		var dbImmitation = new HashSet<Cooperation>(
				Set.of(createdCooperation1, createdCooperation2));
		
		// ensures that Cooperation.delete(id) method is called if no users left within the cooperation		
		doAnswer(i -> dbImmitation.remove(createdCooperation2)).when(repository).delete(any());
		
		service.leaveCooperation(createdCooperation1.getId());
		
		assertFalse(dbImmitation.contains(createdCooperation2));
				
	}
	
	
	@Test
	@DisplayName("Joining a cooperation via invitation")
	public void testJoinViaInvitation() {
		when(invitationService.getCooperationId(any()))
			.thenReturn(2l);
		
		when(repository.findById(any()))
			.thenReturn(Optional.of(createdCooperation2));
		
		when(repository.save(any()))
			.thenReturn(createdCooperation2);
		
		when(userService.getCurrentUser())
			.thenReturn(user2);
		
		when(encoder.matches(any(), any())).thenReturn(true);
		
		assertFalse(createdCooperation2.getUsers().contains(user2));
		
		var result = service.joinViaInvitation(new LoginRequest("login", "12345678"));
		
		assertEquals(ResponseEntity.ok().body(createdCooperation2), result);
		
		assertTrue(createdCooperation2.getUsers().contains(user2));
	}
	
	@Test
	@DisplayName("User leaves a not-existing cooperation")
	public void testLeaveNotExistingCooperation() {
				
		when(repository.findById(any())).thenReturn(Optional.empty());
		
		assertThrows(CooperationNotFoundException.class, () -> service.leaveCooperation(createdCooperation1.getId()));
			
	}
	
	@Test
	@DisplayName("Check if user is permitted to change the cooperation")
	public void  testCanUserChangeCooperation() {
		assertTrue(service.canUserChangeCooperation(createdCooperation2, user1));
		assertFalse(service.canUserChangeCooperation(createdCooperation2, user2));
		
		when(repository.findById(any())).thenReturn(Optional.of(createdCooperation2));
		
		assertTrue(service.canUserChangeCooperation(2l, user1));
		assertFalse(service.canUserChangeCooperation(2l, user2));
		
	}
	
	

}
