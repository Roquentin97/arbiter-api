package com.roquentin.arbiter.dto;

import java.util.HashSet;
import java.util.Set;

import com.roquentin.arbiter.models.Convention;
import com.roquentin.arbiter.models.Cooperation;

public class CooperationDTO {
	private Long id;
	private String name;
	private String description;
	private Set<Convention> conventions;
	private Set<UserIdUsernameNameDTO> users;
	
	public CooperationDTO(Cooperation cooperation) {
		id = cooperation.getId();
		name = cooperation.getName();
		description = cooperation.getDescription();
		conventions = cooperation.getConventions();
		users = new HashSet<>();
		cooperation.getUsers().forEach(user -> users.add(new UserIdUsernameNameDTO(user)));
	}
}
