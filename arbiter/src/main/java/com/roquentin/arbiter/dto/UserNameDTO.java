package com.roquentin.arbiter.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserNameDTO {
	
	@Size(min = 3, max = 35, message = "Name must contain at least 3 symbols but not more than 35.")
	@Pattern(regexp = "^([a-zA-Z]*[ ]?){1,3}$", message = "You can give only 3 names separated by single whitespace or dash. Names can contain only letters")
	private String name;
	
	public UserNameDTO(String name) {
		this.name = name;
	}
	
	public String getName() {return name;}
}
