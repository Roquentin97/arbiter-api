package com.roquentin.arbiter.controllers;

import static org.mockito.Mockito.doReturn;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roquentin.arbiter.dto.ConventionDTO;
import com.roquentin.arbiter.models.Convention;
import com.roquentin.arbiter.models.Cooperation;
import com.roquentin.arbiter.services.ConventionService;

@SpringBootTest
@AutoConfigureMockMvc
public class ConventionControllerTest {
	
	@MockBean
	private ConventionService service;
	
	@Autowired
	private  MockMvc mocMvc;
	
	private static Cooperation cooperation;
	private static ConventionDTO toPost;
	
	@BeforeAll
	static void init() {
		cooperation = new Cooperation();
		cooperation.setId(1l);
		cooperation.setName("cooperation 1");
		cooperation.setPassword("12345678aA$#");
		
		toPost = new ConventionDTO();
		toPost.setName("first convention");
		toPost.setDescription("first convention description");
		toPost.setCooperationId(1l);
		toPost.setConsequence("first convention`s consequence");
	}
	
	
	
	
	@Test
	@WithMockUser
	@DisplayName("valid POST /api/convention/create")
	void testCreateConventionWithValidArguments() throws Exception{
		
		doReturn(true).when(service).createConvention(any());
		
		 mocMvc.perform(post("/api/convention/create")
			.contentType(MediaType.APPLICATION_JSON)
			.content(asJsonString(toPost)))
			
			.andExpect(status().isCreated());
		
			
	}
	
	@Test
	@WithMockUser
	@DisplayName("invalid POST /api/convention/create")
	void testCreateConventionWithinValidArguments() throws Exception{
		
		doReturn(true).when(service).createConvention(any());
		
		ConventionDTO post = new ConventionDTO(toPost);
		
		post.setName("123");
		
		MvcResult result = mocMvc.perform(post("/api/convention/create")
			.contentType(MediaType.APPLICATION_JSON)
			.content(asJsonString(post)))
			.andExpect(status().is4xxClientError())
			.andReturn();

		assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException);
		
		post.setName(toPost.getName());

		post.setDescription("123");
		
		result = mocMvc.perform(post("/api/convention/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(post)))
				.andExpect(status().is4xxClientError())
				.andReturn();

		assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException);
		 
		post.setDescription(toPost.getDescription());
		
		post.setConsequence("123");
		
		result = mocMvc.perform(post("/api/convention/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(post)))
				.andExpect(status().is4xxClientError())
				.andReturn();

		assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException);
		 
		 post.setConsequence(toPost.getConsequence());
		 
		 post.setCooperationId(null);
		 
		result = mocMvc.perform(post("/api/convention/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(post)))
				.andExpect(status().is4xxClientError())
				.andReturn();

		assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException);
					
	}
	
	@Test
	@WithMockUser
	@DisplayName("valid REMOVE /api/convention/delete/{id}")
	public void testDeleteConvention() {
		
	}
	
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	

}
