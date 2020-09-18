package com.roquentin.arbiter.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roquentin.arbiter.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	public Optional<User> findByUsernameIgnoreCase(String username);
	
	public Optional<User> findByEmailIgnoreCase(String email);
	
	public boolean existsByUsernameIgnoreCase(String username);
	
	public boolean existsByEmailIgnoreCase(String email);
}
