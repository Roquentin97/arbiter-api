package com.roquentin.arbiter.controllers;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.roquentin.arbiter.dto.CooperationRefDTO;
import com.roquentin.arbiter.expections.CooperationNotFoundException;
import com.roquentin.arbiter.models.Cooperation;
import com.roquentin.arbiter.models.User;
import com.roquentin.arbiter.services.CooperationService;
import com.roquentin.arbiter.testUtils.MapperHelper;

import lombok.With;


@SpringBootTest
@AutoConfigureMockMvc
public class CooperationControllerTest {

	@MockBean
	private CooperationService service;
	
	@Autowired
	private MockMvc mocMvc;
	
	private static Cooperation cooperationToPost;
	private static User user1, user2;

	
	@BeforeAll
	public static void init() {
		
		user1 = new User();
		user1.setId(1l);
		user1.setName("user1");
		user1.setUsername("useruser1");
		user1.setEmail("user1@gmail.com");
		
		user2 = new User();
		user2.setId(2l);
		user2.setName("user2");
		user2.setUsername("useruser2");
		user2.setEmail("user2@gmail.com");
		
		cooperationToPost = new Cooperation();
		cooperationToPost.setName("First cooperation");
		cooperationToPost.setDescription("Description of the first cooperation");
		cooperationToPost.setPassword("12345678");
		
		
	}
	@Test
	@WithMockUser
	@DisplayName("valid POST /api/cooperation/create")
	public void testCreateCooperation() throws Exception{
		when(service.createCooperation(any()))
			.thenReturn(Map.of("invitation", "0sx000xc0"));
		
		mocMvc.perform(post("/api/cooperation/create")
			.contentType(MediaType.APPLICATION_JSON)
			.content(MapperHelper.asJsonString(cooperationToPost)))
			.andExpect(status().isCreated());
	}
	
	@Test
	@WithMockUser
	@DisplayName("invalid POST /api/cooperation/create")
	public void testCreteCooperationWithInvalidFirlds() throws Exception {
		when(service.createCooperation(any()))
			.thenReturn(Map.of("invitation", "0sx000xs0"));
		
		Cooperation toPost = new Cooperation(cooperationToPost);
		
		toPost.setName("123");
		
		MvcResult result = mocMvc.perform(
				post("/api/cooperation/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(MapperHelper.asJsonString(toPost)))
				.andExpect(status().is4xxClientError())
				.andReturn();
		
		assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException);
		assertTrue(result.getResolvedException().getMessage().contains("name"));
		
		toPost.setName(cooperationToPost.getName());
		
		
		
		toPost.setDescription("123");
		
		result = mocMvc.perform(
				post("/api/cooperation/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(MapperHelper.asJsonString(toPost)))
				.andExpect(status().is4xxClientError())
				.andReturn();
		
		assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException);
		assertTrue(result.getResolvedException().getMessage().contains("Description"));
		
		toPost.setDescription(cooperationToPost.getDescription());
		
		toPost.setPassword(null);
		
		result = mocMvc.perform(
				post("/api/cooperation/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(MapperHelper.asJsonString(toPost)))
				.andExpect(status().is4xxClientError())
				.andReturn();
		
		assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException);
		assertTrue(result.getResolvedException().getMessage().contains("password"));
		
		toPost.setPassword(cooperationToPost.getPassword());
		
	}
	
	@Test
	@WithMockUser
	@DisplayName("get /api/cooperation/list should return list of all user's cooperations")
	public void testGetAllForUser() throws Exception{
		when(service.getUsersCooperations())
			.thenReturn(Set.of(new CooperationRefDTO()));
		
		mocMvc.perform(get("/api/cooperation/list"))
			.andExpect(status().isOk());
	}
	
	@Test
	@WithMockUser
	@DisplayName("get /api/cooperation/leave/{id}")
	public void testLeaveCooperation() throws Exception{

		mocMvc.perform(get("/api/cooperation/leave/0"))
			.andExpect(status().isOk());
		
	}
	
	
}
