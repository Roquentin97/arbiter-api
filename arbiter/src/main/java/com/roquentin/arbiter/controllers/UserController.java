package com.roquentin.arbiter.controllers;

import javax.validation.Valid;
import javax.validation.constraints.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.roquentin.arbiter.dto.UserPasswordUpdateDTO;
import com.roquentin.arbiter.dto.UserRefDTO;
import com.roquentin.arbiter.services.UserService;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService service;
	
	@PutMapping("/update/password")
	@ResponseStatus(code = HttpStatus.OK, reason = "Password updated successfully")
	public void updatePassword(@Valid @RequestBody UserPasswordUpdateDTO pswdDTO) {
		 service.updatePassword(pswdDTO);
	}
	
	@PutMapping("/update/email")
	@ResponseStatus(code = HttpStatus.OK, reason = "Email updated successfully")
	public void updateEmail(@Email @RequestBody String newEmail) {
		 service.updateEmail(newEmail);
	}
	
	@PutMapping("/update/name")
	@ResponseStatus(code = HttpStatus.OK, reason = "Name updated successfully")
	public void updateName(@Valid UserRefDTO userName) {
		 service.updateName(userName);
	}
	
	
	

}
