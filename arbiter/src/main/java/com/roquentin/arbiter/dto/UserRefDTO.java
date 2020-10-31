package com.roquentin.arbiter.dto;

import javax.persistence.Id;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserRefDTO {
	
	@Id
	private Long id;
	
	@Size(min = 3, max = 10, message = "Username length must contain at least 3 symbols long but not more than 10")
	@Pattern(regexp = "^\\w*$", message = "Username cannot contain only letters, digits and underscores")
	private String username;
	
	@Size(min = 3, max = 35, message = "Name must contain at least 3 symbols but not more than 35.")
	@Pattern(regexp = "^([a-zA-Z]*[ ]?){1,3}$", message = "You can give only 3 names separated by single whitespace or dash. Names can contain only letters")
	private String name; 
}
