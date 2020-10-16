package com.roquentin.arbiter.dto;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CooperationRefDTO {
	private Long id;
	
	@Size(min = 4, max = 45, message = "Name must include at least 4 symbols but not more than 45.")
	@Pattern(regexp = "^[\\w| |'|`]*$", message = "Name can include only letters, numbers, whitespaces, apostrophes, and underscores.")
	private String name;
	
	@Size(min = 5, max = 140, message = "Description canot be shorter than 5 characters and longer than 140.")
	private String description;
	
	public CooperationRefDTO() {};
	
	public CooperationRefDTO(Long id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	};
	
}
