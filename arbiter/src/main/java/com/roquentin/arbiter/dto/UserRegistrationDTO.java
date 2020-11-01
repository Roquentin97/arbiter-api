package com.roquentin.arbiter.dto;

import java.util.Locale;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.roquentin.arbiter.models.validators.ValidEmail;

import lombok.Data;

@Data
public class UserRegistrationDTO {

	@ValidEmail
	@NotEmpty
	private String email;
	
	private String username;
	
	private String password;
	private String confirmPassword;

}
