package com.roquentin.arbiter.services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.roquentin.arbiter.expections.UserNotFoundException;
import com.roquentin.arbiter.models.User;
import com.roquentin.arbiter.payloads.requests.LoginRequest;
import com.roquentin.arbiter.payloads.responses.ErrorResponse;
import com.roquentin.arbiter.payloads.responses.Responses;
import com.roquentin.arbiter.payloads.responses.ErrorResponse.Causes;
import com.roquentin.arbiter.repositories.UserRepository;
import com.roquentin.arbiter.security.UserDetailsImpl;
import com.roquentin.arbiter.security.jwt.JwtUtils;


@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	private User currentUser;
	
	public ResponseEntity<?> createUser( User newUser) {
		Map<String, String> errors = new HashMap<>();
		if (repository.existsByUsernameIgnoreCase(newUser.getUsername())) {
			errors.put("username", "Usernmae " + newUser.getUsername() + " has been taken.");
		}
		if (repository.existsByEmailIgnoreCase(newUser.getEmail())) {
			errors.put("email", "Email " + newUser.getEmail() + " is alrady in use");
		}

		if (!errors.isEmpty())
			return ResponseEntity.badRequest().body(Responses.errorResponse(Causes.INTEGRITY_EXCEPTION, errors));

		newUser.setRoles(Set.of(roleService.getRoleByName("ROLE_SIMPLE_USER")));
		newUser.setPassword(encoder.encode(newUser.getPassword()));
		repository.save(newUser);
		return ResponseEntity.ok().body("Successfully registration");
	}
	
	
	public ResponseEntity<?> login(LoginRequest request){
		String identifier = request.getIdentifier();
		String username =  (identifier.indexOf("@") != -1) ? 
				repository.findByEmailIgnoreCase(identifier).orElseGet(User::new).getUsername() :
				identifier;
				
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(username, request.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		
		return ResponseEntity.ok().body(Responses.jwtResponse(jwt, userDetails));
 
	}
	
	public User getCurrentUser() {
		if (currentUser == null)
			currentUser = repository.findByUsernameIgnoreCase(
					SecurityContextHolder.getContext().getAuthentication().getName())
						.orElseThrow(UserNotFoundException::new);
			
			return currentUser;
	}
}
