package com.roquentin.arbiter.services;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.roquentin.arbiter.dto.UserRegistrationDTO;
import com.roquentin.arbiter.exceptions.PasswordMismatchException;
import com.roquentin.arbiter.exceptions.UnauthorizedException;
import com.roquentin.arbiter.exceptions.UniqueKeyViolationException;
import com.roquentin.arbiter.exceptions.UserNotFoundException;
import com.roquentin.arbiter.models.RegistrationVerificationToken;
import com.roquentin.arbiter.models.User;
import com.roquentin.arbiter.payloads.requests.LoginRequest;
import com.roquentin.arbiter.payloads.responses.JwtResponse;
import com.roquentin.arbiter.payloads.responses.Responses;
import com.roquentin.arbiter.repositories.RegistrationVerificationTokenRepository;
import com.roquentin.arbiter.repositories.UserRepository;
import com.roquentin.arbiter.security.UserDetailsImpl;
import com.roquentin.arbiter.security.jwt.JwtUtils;


@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private RegistrationVerificationTokenRepository tokenRepository;

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	private User currentUser;
	
	@Transactional
	public User createUser( UserRegistrationDTO newUser) {
		Map<String, String> errors = new HashMap<>();
		if (repository.existsByUsernameIgnoreCase(newUser.getUsername())) {
			errors.put("username", "Usernmae " + newUser.getUsername() + " has been taken.");
		}
		if (repository.existsByEmailIgnoreCase(newUser.getEmail())) {
			errors.put("email", "Email " + newUser.getEmail() + " is alrady in use");
		}

		if (!errors.isEmpty())
			throw new UniqueKeyViolationException(errors);
		
		User user = new User();
		user.setEmail(newUser.getEmail());
		user.setUsername(newUser.getUsername());

		
		//TODO move to constraint / validator
		if (! newUser.getPassword().equals(newUser.getConfirmPassword()))
			throw new PasswordMismatchException();
		
		user.setPassword(encoder.encode(newUser.getPassword()));
		
		//TODO:: get name from properties
		user.setRoles(Set.of(roleService.getRoleByName("ROLE_SIMPLE_USER")));
		
		return repository.save(user);
	
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
					SecurityContextHolder
					.getContext()
					.getAuthentication()
					.getName())
						.orElseThrow(UserNotFoundException::new);
			
			return currentUser;
	}

	@Transactional
	public void updatePassword(UserPasswordUpdateDTO pswdDTO) {
				
		if (!encoder.matches(pswdDTO.getOldPassword(), getCurrentUser().getPassword()))
			throw new UnauthorizedException("Actual password mismatch");
	
		currentUser.setPassword(encoder.encode(pswdDTO.getNewPassword()));
		currentUser = repository.save(currentUser);

	}
	
	@Transactional
	public void updateEmail(String email) {
		User user = new User(currentUser);
		
		if (repository.existsByEmailIgnoreCase(email))
			throw new UniqueKeyViolationException(Map.of("email", email));
		
		//TODO: email verification
		user.setEmail(email);
		
		currentUser = repository.save(user);
	}
	
	// TODO change to update users not identity specific information
	@Transactional
	public void updateName(UserRefDTO nameDTO) {
		getCurrentUser().setName(nameDTO.getName());
		currentUser = repository.save(currentUser);

	}
	
	//TODO add unit test
	public void createVerificationToken(User user, String token) {
		RegistrationVerificationToken vToken = new RegistrationVerificationToken(user, token);
		tokenRepository.save(vToken);
	}
	
	public ResponseEntity<?> confirmRegistration(String token) {
		var vToken = tokenRepository.findByToken(token);
		
		if (vToken == null)
			return new ResponseEntity<String>("Invalid token", HttpStatus.BAD_REQUEST);
		
		Calendar cal = Calendar.getInstance();
		if (vToken.getExpiryDate().getTime() - cal.getTime().getTime() <= 0)
			return new ResponseEntity<String>("Token expired", HttpStatus.FORBIDDEN);
		
		User user = vToken.getUser();
		user.setEnabled(true);
		
		repository.save(user);
		
		return new ResponseEntity<String>("Registration confirmed successfully", HttpStatus.OK);
	}
}
