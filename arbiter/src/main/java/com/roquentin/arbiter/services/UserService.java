package com.roquentin.arbiter.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.roquentin.arbiter.dto.UserPasswordUpdateDTO;
import com.roquentin.arbiter.dto.UserRefDTO;
import com.roquentin.arbiter.expections.UnauthorizedException;
import com.roquentin.arbiter.expections.UniqueKeyViolationException;
import com.roquentin.arbiter.expections.UserNotFoundException;
import com.roquentin.arbiter.models.User;
import com.roquentin.arbiter.payloads.requests.LoginRequest;
import com.roquentin.arbiter.payloads.responses.JwtResponse;
import com.roquentin.arbiter.payloads.responses.Responses;
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
	
	public void createUser( User newUser) {
		Map<String, String> errors = new HashMap<>();
		if (repository.existsByUsernameIgnoreCase(newUser.getUsername())) {
			errors.put("username", "Usernmae " + newUser.getUsername() + " has been taken.");
		}
		if (repository.existsByEmailIgnoreCase(newUser.getEmail())) {
			errors.put("email", "Email " + newUser.getEmail() + " is alrady in use");
		}

		if (!errors.isEmpty())
			throw new UniqueKeyViolationException(errors);
		
		newUser.setRoles(Set.of(roleService.getRoleByName("ROLE_SIMPLE_USER")));
		newUser.setPassword(encoder.encode(newUser.getPassword()));
		repository.save(newUser);
	
	}
	
	
	public JwtResponse login(LoginRequest request){
		
		// UserDetailsImpl performs check whether username or email is used to log in 
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getIdentifier(), request.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		
		return Responses.jwtResponse(jwt, userDetails);
 
	}
	
	public User getCurrentUser() {
		if (currentUser == null)
			currentUser = repository.findByUsernameIgnoreCase(
					SecurityContextHolder.getContext().getAuthentication().getName())
						.orElseThrow(UserNotFoundException::new);
			
			return currentUser;
	}
	
	public void updatePassword(UserPasswordUpdateDTO pswdDTO) {
		User user = new User(currentUser);
		
		
		if (!encoder.matches(pswdDTO.getOldPassword(), user.getPassword()))
			throw new UnauthorizedException("Actual password mismatch");
	
		user.setPassword(encoder.encode(pswdDTO.getNewPassword()));
		currentUser = repository.save(user);

	}
	
	public void updateEmail(String email) {
		User user = new User(currentUser);
		
		if (repository.existsByEmailIgnoreCase(email))
			throw new UniqueKeyViolationException(Map.of("email", email));
		
		//TODO: email verification
		user.setEmail(email);
		
		currentUser = repository.save(user);
	}
	
	public void updateName(UserRefDTO nameDTO) {
		currentUser.setName(nameDTO.getName());
		currentUser = repository.save(currentUser);

	}
}
