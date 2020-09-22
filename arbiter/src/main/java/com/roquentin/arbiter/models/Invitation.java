package com.roquentin.arbiter.models;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "invitations")
@Data
public class Invitation {
	
	@Id
	@Column(nullable = false, unique = true)
	private String code;
	
	public Invitation(String code) {
		this.code = code;
	}
	
	public Invitation() {}

}
