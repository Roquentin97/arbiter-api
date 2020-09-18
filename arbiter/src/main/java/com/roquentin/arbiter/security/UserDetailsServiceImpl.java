package com.roquentin.arbiter.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.roquentin.arbiter.models.User;
import com.roquentin.arbiter.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository repository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) {
		User user = repository.findByUsernameIgnoreCase(username)
				.orElseThrow(
						() -> new UsernameNotFoundException("User " + username + " not found!")
				);

		return UserDetailsImpl.build(user);		
	}
}
