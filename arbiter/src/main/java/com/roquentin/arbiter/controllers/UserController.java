package com.roquentin.arbiter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.roquentin.arbiter.models.User;
import com.roquentin.arbiter.services.UserService;

@CrossOrigin
@RestController("/api/user")
public class UserController {

	@Autowired
	private UserService userService;
	

}
