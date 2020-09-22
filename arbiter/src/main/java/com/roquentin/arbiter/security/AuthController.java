package com.roquentin.arbiter.security;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.roquentin.arbiter.models.User;
import com.roquentin.arbiter.payloads.requests.LoginRequest;
import com.roquentin.arbiter.payloads.responses.JwtResponse;
import com.roquentin.arbiter.services.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	
	@Autowired
	private UserService userService;
	
	@PostMapping("/signup")
	@ResponseStatus(code = HttpStatus.CREATED, reason = "User registered successfully")
	public void signUserUp(@Valid @RequestBody User newUser){
		 userService.createUser(newUser);
	}

	@PostMapping("/signin")
	@ResponseStatus(HttpStatus.OK)
	public JwtResponse autchenticateUser(@RequestBody LoginRequest loginRequest){
		return userService.login(loginRequest);
	}
	
	@GetMapping("/test")
	@ResponseStatus(HttpStatus.OK)
	public void isTokenValid(){
		
	}

}


