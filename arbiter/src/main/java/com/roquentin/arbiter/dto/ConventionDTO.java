package com.roquentin.arbiter.dto;

import javax.persistence.Column;

import javax.validation.constraints.NotNull;

import com.roquentin.arbiter.models.validators.ValidConventionsDetails;
import com.roquentin.arbiter.models.validators.ValidConventionsName;

import lombok.Data;

@Data
public class ConventionDTO {
	private Long id;
	
	
	@ValidConventionsName
	@Column(nullable = false)
	private String name;
	
	@ValidConventionsDetails
	private String description;
	
	@ValidConventionsDetails
	private String consequence;

	@NotNull
	private Long cooperationId;
	
	public ConventionDTO() {}
	
	public ConventionDTO(ConventionDTO toClone) {
		this.name = toClone.name;
		this.description = toClone.description;
		this.cooperationId = toClone.cooperationId;
		this.consequence = toClone.consequence;
	}
}
