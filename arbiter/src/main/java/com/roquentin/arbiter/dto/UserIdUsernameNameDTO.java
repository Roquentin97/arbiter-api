package com.roquentin.arbiter.dto;

import com.roquentin.arbiter.models.User;

public class UserIdUsernameNameDTO {
	
	private Long id;
	
	private String username;
	
	private String name;
	
	public UserIdUsernameNameDTO(User user) {
		id = user.getId();
		username = user.getUsername();
		name = user.getName();
	}

}
