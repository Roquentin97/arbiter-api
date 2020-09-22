package com.roquentin.arbiter.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Embeddable
@Data
public class UserCooperation implements Serializable {

	@Column(name="user_id")
	private Long userId;
	
	@Column(name = "cooperation_id")
	private Long cooperationId;
	
	public UserCooperation() {}
	
	public UserCooperation(Long userId, Long cooperationId) {
		this.userId = userId;
		this.cooperationId = cooperationId;
	}
}
