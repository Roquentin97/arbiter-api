package com.roquentin.arbiter.repositories;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataJpaTest
@ExtendWith(SpringExtension.class)
public class UserRepositoryIntegrationTest {
	
	@Autowired
	private TestEntityManager manager;
	
	@Autowired
	private UserRepository respository;

}