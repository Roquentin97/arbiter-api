package com.roquentin.arbiter.models;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class RegistrationVerificationToken {

	public RegistrationVerificationToken() {}
	public RegistrationVerificationToken(User user, String token) {
		this.user = user;
		this.token = token;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String token;
	
	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id")
	private User user;
	
	@Column(nullable = false)
	private Date expiryDate = calculateExpiryDate(24);
	
	private Date calculateExpiryDate(int expiryTimeInHours) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Timestamp(cal.getTime().getTime()));
		cal.add(Calendar.HOUR, expiryTimeInHours);
		return new Date(cal.getTime().getTime());
	}
	
	
}
