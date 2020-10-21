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
	// "login" is used instead of "username" because the implementation
	// overrides original behavior and performs check whether username OR email is used
	// as a conceptual trade-off to avoid multiple DB access 
	public UserDetails loadUserByUsername(String login) {
		
		
		
		User user = (! login.contains("@") ?
				repository.findByUsernameIgnoreCase(login)
				.orElseThrow(
						() -> new UsernameNotFoundException("User with username: " + login + " not found!")
				) :
				
				repository.findByEmailIgnoreCase(login).orElseThrow(
						() -> new UsernameNotFoundException("User with email: " + login + " not found!"))
				);

		return UserDetailsImpl.build(user);		
	}
}
