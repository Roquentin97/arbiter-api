package com.roquentin.arbiter.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import com.roquentin.arbiter.dto.ConventionDTO;
import com.roquentin.arbiter.models.Convention;
import com.roquentin.arbiter.models.Cooperation;
import com.roquentin.arbiter.models.User;

@DataJpaTest
public class ConventionRepositoryTest {
	
	@Autowired
	private TestEntityManager entitymanager;
	
	@Autowired
	private ConventionRepository repository;
	
	private static ConventionDTO dto;
	private static Cooperation cooperation;
	private static User user1, user2;
	
	@BeforeAll
	public static void init() {
		user1 = new User();
		user1.setId(1l);
		user1.setName("user1");
		user1.setEmail("user1@gmail.com");
		
		user2 = new User();
		user2.setId(2l);
		user2.setName("user2");
		user2.setEmail("user2@gmail.com");
		
		dto = new ConventionDTO();
		dto.setId(1l);
		dto.setName("First convention");
		dto.setDescription("Description of the first convention");
		dto.setConsequence("Consequence of the first convention");
		dto.setCooperationId(1l);
		
		cooperation = new Cooperation();
		cooperation.setId(1l);
		cooperation.setName("first cooperation");
		cooperation.setDescription("description of the first cooperation");
		cooperation.setPassword("password");
		cooperation.setUsers(Set.of(user1));
		
		System.out.println("HI");
	}
	
	@Test
	@Sql( scripts = "cooperations.sql")
	@Rollback(true)
	public void testSave() {
		Convention convention = new Convention();
		convention.setName("name");
		convention.setDescription("description");
		convention.setConsequence("consequence");
		convention.setCooperation(cooperation);
		
		repository.save(convention);
	
		assertEquals(1, repository.count());
		assertEquals(convention, repository.getOne(1l));
	}
	
	@Test
	@Modifying(flushAutomatically = true)
	@Sql("cooperations.sql")
	public void testSaveUsingDTO() {
		
		
		repository.saveUsingDTO(
				dto.getName(), dto.getDescription(), dto.getCooperationId(), dto.getConsequence());
		
		
		
		assertEquals(1, repository.count());
		
		// is used since sequence generating id may be changed by previous tests
		Convention convention = repository.findAll().get(0);
		
		assertEquals(dto.getName(), convention.getName());
		assertEquals(dto.getDescription(), convention.getDescription());
		assertEquals(dto.getConsequence(), convention.getConsequence());
		assertEquals(cooperation.getId(), convention.getCooperation().getId());
		assertEquals(cooperation.getName(), convention.getCooperation().getName());
		
	}
	
	
}
