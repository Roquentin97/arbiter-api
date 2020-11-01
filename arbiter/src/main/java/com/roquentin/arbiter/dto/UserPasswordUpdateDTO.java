package com.roquentin.arbiter.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserPasswordUpdateDTO {

	private Long id;
	private String oldPassword;
	
	@Size(min = 8, max = 30, message = "Password must include at least 8 symbols but not more than 30")
	@Pattern(regexp = "^(.*[a-z].*)(.*[A-Z].*)(.*\\d.*)(.*[@|#|$|%|^|&|!|?|*].*)$",
			message = "Password must include at least one letter in uppercase and lowercase, one digit and 1 special character (@,#,$,%,^,&,?,!)")
	private String newPassword;
}
