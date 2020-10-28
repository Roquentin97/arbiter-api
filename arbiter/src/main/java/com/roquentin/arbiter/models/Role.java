package com.roquentin.arbiter.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Data
@Entity
@Table(name = "roles")
public class Role {
	@Id @GeneratedValue
	private Integer id;
	
	@Column(nullable = false, unique = true)
	@Pattern(regexp ="^ROLE_([A-Z]){4,50}$")
	private String name;
	
	public Role(String name) {
		this.name = name;
	}

	
}
