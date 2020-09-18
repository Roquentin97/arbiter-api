package com.roquentin.arbiter.security;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.roquentin.arbiter.models.User;
import com.roquentin.arbiter.payloads.requests.LoginRequest;
import com.roquentin.arbiter.security.jwt.JwtUtils;
import com.roquentin.arbiter.services.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/signup")
	public ResponseEntity<?> signUserUp(@Valid @RequestBody User newUser){
		return userService.createUser(newUser);
	}

	@PostMapping("/signin") 
	public ResponseEntity<?> autchenticateUser(@RequestBody LoginRequest loginRequest){
		return userService.login(loginRequest);
	}
	
	@GetMapping("/test")
	public ResponseEntity<?> isTokenValid(){
		return ResponseEntity.ok("Token valid");
	}
}


