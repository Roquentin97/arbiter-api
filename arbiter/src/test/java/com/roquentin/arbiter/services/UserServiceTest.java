package com.roquentin.arbiter.services;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import com.roquentin.arbiter.dto.UserPasswordUpdateDTO;
import com.roquentin.arbiter.dto.UserRegistrationDTO;
import com.roquentin.arbiter.exceptions.PasswordMismatchException;
import com.roquentin.arbiter.exceptions.UniqueKeyViolationException;
import com.roquentin.arbiter.models.Role;
import com.roquentin.arbiter.models.User;
import com.roquentin.arbiter.payloads.requests.LoginRequest;
import com.roquentin.arbiter.payloads.responses.JwtResponse;
import com.roquentin.arbiter.payloads.responses.Responses;
import com.roquentin.arbiter.repositories.UserRepository;
import com.roquentin.arbiter.security.UserDetailsImpl;
import com.roquentin.arbiter.security.jwt.JwtUtils;

@SpringBootTest
public class UserServiceTest {
	
	@Autowired
	private UserService service;
	
	@MockBean
	private RoleService roleService;
	
	@MockBean
	private UserRepository repository;
	
	@MockBean
	private JwtUtils jwtUtils;
	
	@MockBean
	private PasswordEncoder encoder;
	
	@MockBean
	private AuthenticationManager authenticationManager;
	
	@MockBean
	private SecurityContextHolder contextHolder;
	
	private static User user1;
	private static Authentication authentication;
	private static UserDetailsImpl userDetails;
	private static UserRegistrationDTO userRegistrationDTO;
	
	
	@BeforeAll
	public static void init() {
		user1 = new User();
		user1.setUsername("useruser1");
		user1.setEmail("user1@gmail.com");
		user1.setPassword("$#12345678a");
		user1.setName("User one");
		user1.setRoles(Set.of(new Role("ROLE_SIMPLE_USER")));
		
		userRegistrationDTO = new UserRegistrationDTO();
		userRegistrationDTO.setEmail(user1.getEmail());
		userRegistrationDTO.setUsername(user1.getUsername());
		userRegistrationDTO.setPassword(user1.getPassword());
		userRegistrationDTO.setConfirmPassword(user1.getPassword());
	
		
		userDetails = new UserDetailsImpl(1l, user1.getUsername(), user1.getEmail(), user1.getPassword(), user1.getRoles());
		
		authentication = new Authentication() {
			
			@Override
			public String getName() {
				return "useruser1";
			}
			
			@Override
			public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
				isAuthenticated = true;
			}
			
			@Override
			public boolean isAuthenticated() {
				return isAuthenticated();
			}
			
			@Override
			public Object getPrincipal() {
				return userDetails;
			}
			
			@Override
			public Object getDetails() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object getCredentials() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Collection<? extends GrantedAuthority> getAuthorities() {
				return userDetails.getAuthorities();
			}
		};
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
	
	// replace with integration test
	@Test
	@DisplayName("Valid user creation")
	public void testCreateUserValid() {

		String password = "encoded";
		
		when(repository.existsByUsernameIgnoreCase(any()))
			.thenReturn(false);
		
		when(repository.existsByEmailIgnoreCase(any()))
			.thenReturn(false);
		
		when(repository.save(any()))
			.then(u -> {
				user1.setId(1l);
				user1.setPassword(password);
				return user1;
			});
		
		when(encoder.encode(any()))
			.thenReturn(password);
		
		when(roleService.getRoleByName(any()))
			.thenReturn(new Role("ROLE_SIMPLE_USER"));
		
		user1 = service.createUser(userRegistrationDTO);
		
		assertEquals(1l, user1.getId());
		assertEquals(password, user1.getPassword());
		assertEquals(Set.of(new Role("ROLE_SIMPLE_USER")), user1.getRoles());
	
	}
	
	@Test
	@DisplayName("Creation of the user with unique fields already taken")
	public void testCreateUserExisting() {
		when(repository.existsByUsernameIgnoreCase(any()))
			.thenReturn(true);
		
		when(repository.existsByEmailIgnoreCase(any()))
			.thenReturn(false);
		
		when(repository.save(any()))
			.then(u -> {
				user1.setId(1l);
				return user1;
			});
			
		assertThrows(UniqueKeyViolationException.class, 
				() -> service.createUser(userRegistrationDTO));
		
		try {
			service.createUser(userRegistrationDTO);
		} catch (UniqueKeyViolationException e) {
			assertTrue(e.getMessage().contains("username"));
			assertFalse(e.getMessage().contains("email"));
		}
		
		when(repository.existsByUsernameIgnoreCase(any()))
		.thenReturn(false);
		when(repository.existsByEmailIgnoreCase(any()))
		.thenReturn(true);
		
		assertThrows(UniqueKeyViolationException.class, 
				() -> service.createUser(userRegistrationDTO));
		
		try {
			service.createUser(userRegistrationDTO);
		} catch (UniqueKeyViolationException e) {
			assertFalse(e.getMessage().contains("username"));
			assertTrue(e.getMessage().contains("email"));
		}
		
		when(repository.existsByUsernameIgnoreCase(any()))
		.thenReturn(true);
		
		assertThrows(UniqueKeyViolationException.class, 
				() -> service.createUser(userRegistrationDTO));
		
		try {
			service.createUser(userRegistrationDTO);
		} catch (UniqueKeyViolationException e) {
			assertTrue(e.getMessage().contains("username"));
			assertTrue(e.getMessage().contains("email"));
		}
	}
	
	@Test
	@DisplayName("Creation new user with password and password`s confirmation mismatch")
	public void testCreateUserPasswordMismatch() {
		userRegistrationDTO.setConfirmPassword("invalidconfirmation");
		
		String password = "encoded";
		
		when(repository.existsByUsernameIgnoreCase(any()))
			.thenReturn(false);
		
		when(repository.existsByEmailIgnoreCase(any()))
			.thenReturn(false);
		
		assertThrows(PasswordMismatchException.class,
				() -> service.createUser(userRegistrationDTO));
	}
	@Test
	@DisplayName("User logs in via valid username")
	public void testLogin() {
		LoginRequest request = new LoginRequest(
				userDetails.getUsername(), 
				userDetails.getPassword());
		
		String token = "jwt_token";
		
		when(authenticationManager.authenticate(any()))
		.thenReturn(authentication);
		
		when(jwtUtils.generateJwtToken(any()))
		.thenReturn(token);
		
		JwtResponse expectedResponse = Responses.jwtResponse(token, userDetails);
		
		assertEquals(expectedResponse, service.login(request));
				
	}
	
	@Test
	@DisplayName("Login with invalid credentials")
	public void testLoginBadAuthorization() {
		LoginRequest request = new LoginRequest(
				userDetails.getUsername(), 
				userDetails.getPassword());
		
		when(authenticationManager.authenticate(any()))
			.thenThrow(new UsernameNotFoundException("User with given login not found"));
		
		assertThrows(AuthenticationException.class, () -> service.login(request));
	}
	
	@Test
	@WithMockUser
	@DisplayName("User's pasword update")
	public void testUpdatePassword() {
		String newPassword = "new password";
		
		when(repository.findByUsernameIgnoreCase(any()))
			.thenReturn(Optional.of(user1));
		
		when(encoder.matches(any(), any()))
			.thenReturn(true);
		
		when(encoder.encode(any()))
			.thenReturn(newPassword);
		
		
		service.updatePassword(new UserPasswordUpdateDTO());
		
		assertEquals(newPassword, service.getCurrentUser().getPassword());
		
	}
	
}

