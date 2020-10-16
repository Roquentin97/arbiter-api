package com.roquentin.arbiter.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import com.roquentin.arbiter.dto.ConventionDTO;
import com.roquentin.arbiter.dto.CooperationRefDTO;
import com.roquentin.arbiter.models.Cooperation;
import com.roquentin.arbiter.models.User;

@DataJpaTest
public class CooperationRespositoryTest {

	@Autowired
	private CooperationRepository repository;
	
	private static Cooperation cooperation;
	private static User user1, user2;
	
	@BeforeAll
	public static void init() {
		
		user1 = new User();
		user1.setId(1l);
		user1.setName("first user");
		user1.setUsername("useruser1");
		user1.setEmail("user1@gmail.com");
		user1.setPassword("password12345678");
		
		user2 = new User();
		user2.setId(2l);
		user2.setName("second user");
		user2.setUsername("useruser2");
		user2.setEmail("user2@gmail.com");
		user2.setPassword("password12345678");
				
		cooperation = new Cooperation();
		cooperation.setId(1l);
		cooperation.setName("first cooperation");
		cooperation.setDescription("description of the first cooperation");
		cooperation.setPassword("password");
		cooperation.setUsers(Set.of(user1));
		
		
		
	}
	
	@Test
	@SqlGroup({
		@Sql("users.sql"),
		@Sql("cooperations.sql"),
		@Sql("cooperations_users.sql")
	})
	@DisplayName("Custom querying method returnin CooperationRefDTO")
	public void testFindByUsersToRefDTO() {
		Set<CooperationRefDTO> result = repository.findByUsersToRefDTO(user1);
		
		CooperationRefDTO expected = new CooperationRefDTO(1l, "first cooperation", "description of the first cooperation");
		
		assertEquals(3, result.size());
		assertTrue(result.contains(expected));
		
	}
	
}
