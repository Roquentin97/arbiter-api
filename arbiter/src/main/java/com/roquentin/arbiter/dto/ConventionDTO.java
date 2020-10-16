package com.roquentin.arbiter.dto;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.roquentin.arbiter.models.Cooperation;

import lombok.Data;

@Data
public class ConventionDTO {
	private Long id;
	
	
	@Pattern(regexp = "^[\\w| |'|`]*$", message = "Name can include only letters, numbers, whitespaces, apostrophes, and underscores.")
	@Size(min = 4, max = 45)
	@Column(nullable = false)
	private String name;
	
	@Size(min = 5, max = 1024, message = "Description canot be shorter than 5 characters and longer than 1024.")
	@Column(nullable = false)
	private String description;
	
	@Size(min = 5, max = 1024, message = "Description canot be shorter than 5 characters and longer than 1024.")
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
