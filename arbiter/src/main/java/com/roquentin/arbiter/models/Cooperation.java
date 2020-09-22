package com.roquentin.arbiter.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Data
@Table(name = "cooperations")
public class Cooperation {
	@Id @GeneratedValue
	private Long id;
	
	@Size(min = 4, max = 45, message = "Name must include at least 4 symbols but not more than 45.")
	@Pattern(regexp = "^[\\w| |'|`]*$", message = "Name can include only letters, numbers, whitespaces, apostrophes, and underscores.")
	@Column(nullable = false)
	private String name;
	
	@Size(min = 5, max = 140, message = "Description canot be shorter than 5 characters and longer than 140.")
	private String description;
	
	@Column(nullable = false)
	private String password;
	
	@OneToMany(mappedBy = "cooperation", targetEntity = Convention.class,
			fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonManagedReference(value = "cooperation_convention")
	private Set<Convention> conventions;
	
	
	@ManyToMany
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	private Set<User> users;
	
	
	
	
	
	
	
}
