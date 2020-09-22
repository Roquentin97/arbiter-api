package com.roquentin.arbiter.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserUpdatePasswordDTO {
	
	private Long id;
	private String oldPassword;
	
	@Size(min = 8, max = 30, message = "Password must include at leact 8 characters but not more than 30")
	@Pattern(regexp = "^(.*[a-z].*)(.*[A-Z].*)(.*\\d.*)(.*[@|#|$|%|^|&|!|?|*].*)$", message = "Your password must include at least\n one lowercaseLeeter\n one uppercase letter\n one digit\n and one special char (@, #, $, %, ^, &, *, ?, !")
	private String newPassword;

	public UserUpdatePasswordDTO(Long id, String oldPassword, String newPassword) {
		this.id = id;
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
	}
	
	public Long getId() {return id;}
	public String getOldPassword() { return oldPassword;}
	public String getNewPassword() { return newPassword;}
}
