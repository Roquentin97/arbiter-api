package com.roquentin.arbiter.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Data
@Table(name = "conventions")
public class Convention {
	
	@Id @GeneratedValue
	private Long id;
	
	@Size(min = 4, max = 45, message = "Name must include at least 4 symbols but not more than 45.")
	@Pattern(regexp = "^[\\w| |'|`]*$", message = "Name can include only letters, numbers, whitespaces, apostrophes, and underscores.")
	@Column(nullable = false)
	private String name;
	
	@Size(min = 5, max = 1024, message = "Description canot be shorter than 5 characters and longer than 1024.")
	@Column(nullable = false)
	private String description;
	
	@Size(min = 5, max = 1024, message = "Description canot be shorter than 5 characters and longer than 1024.")
	private String consequence;
	
	
	@ManyToOne(optional = true)
	@JoinColumn(name = "cooperation_id")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonBackReference("cooperation_convention")
	private Cooperation cooperation;
	
	
	public Convention() {}
	
	public Convention(Long id, String name, String description, String consequence, Cooperation cooperation) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.consequence = consequence;
		this.cooperation = cooperation;
	}
	
	public Convention(Convention convention) {
		id = convention.id;
		name = convention.name;
		description = convention.description;
		consequence = convention.consequence;
		cooperation = convention.cooperation;
	}
	

}
