package com.roquentin.arbiter.controllers;

import static org.mockito.Mockito.doReturn;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.*;

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

import com.fasterxml.jackson.databind.ObjectMapper;
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
	
	@BeforeAll
	static void init() {
		cooperation = new Cooperation();
		cooperation.setId(1l);
	}
	
	
	
	
	@Test
	@WithMockUser
	@DisplayName("POST /api/convention/create")
	void testCreateConvention() throws Exception{
		
		Convention toPost = new Convention(
				null, 
				"newConvention", 
				"conventionDesc", 
				"conventionConsequence",
				cooperation
			); 
		
		Convention toReturn = new Convention(toPost);
		toReturn.setId(1l);
		
		doReturn(toReturn).when(service).createConvention(any());
		
		 mocMvc.perform(post("/api/convention/create")
			.contentType(MediaType.APPLICATION_JSON)
			.content(asJsonString(toPost)))
			
			.andExpect(status().isCreated())
			
			.andExpect(jsonPath("$.id", is(1)))
			.andExpect(jsonPath("$.name", is("newConvention")))
			.andExpect(jsonPath("$.description", is("conventionDesc")))
			.andExpect(jsonPath("$.consequence", is("conventionConsequence")))
			.andExpect(jsonPath("$.cooperation", is(cooperation)));
		
			
	}
	
    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	

}
