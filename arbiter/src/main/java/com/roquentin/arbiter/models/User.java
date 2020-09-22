package com.roquentin.arbiter.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
	
	@Id @GeneratedValue
	private Long id;
	
	@Column(nullable = false, unique = true)
	@Size(min = 3, max = 10, message = "Username length must contain at least 3 symbols long but not more than 10")
	@Pattern(regexp = "^\\w*$", message = "Username cannot contain only letters, digits and underscores")
	private String username;
	
	@Size(min = 3, max = 35, message = "Name must contain at least 3 symbols but not more than 35.")
	@Pattern(regexp = "^([a-zA-Z]*[ ]?){1,3}$", message = "You can give only 3 names separated by single whitespace or dash. Names can contain only letters")
	private String name; 
	
	@Column(nullable = false, unique = true)
	@Email
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	
	/*
	 * @Lob
	 * private byte[] data;
	 */
	
	@ManyToMany
    @JoinTable( 
            name = "users_roles", 
            joinColumns = @JoinColumn(
              name = "user_id", referencedColumnName = "id"), 
            inverseJoinColumns = @JoinColumn(
              name = "role_id", referencedColumnName = "id"))
	private Set<Role> roles;
	
	@Override
	public int hashCode() {
		return  id.hashCode();
	}
	
	public User(User user) {
		id = user.id;
		username = user.username;
		name = user.name;
		email = user.email;
		password = user.password;
	}
	
	public User() {}
}
