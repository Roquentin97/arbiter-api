package com.roquentin.arbiter.dto;

import java.util.Set;

import com.roquentin.arbiter.models.Convention;
import com.roquentin.arbiter.models.validators.ValidCooperationsDescription;
import com.roquentin.arbiter.models.validators.ValidCooperationsName;

import lombok.Data;

@Data
public class CooperationFullDTO {

	private Long id;
	
	@ValidCooperationsName
	private String name;
	
	@ValidCooperationsDescription
	private String description;
	
	private Set<UserRefDTO> users;
	
	private Set<Convention> conventions;
	
}
