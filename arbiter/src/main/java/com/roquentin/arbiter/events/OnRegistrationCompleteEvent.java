package com.roquentin.arbiter.events;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.roquentin.arbiter.models.User;

import lombok.Data;

@Data
public class OnRegistrationCompleteEvent extends ApplicationEvent{

	//private Locale locale;
	private User user;
	
	// TODO add Localre argument
	public OnRegistrationCompleteEvent(User user) {
		super(user);
		
		this.user = user;
		//this.locale = locale;
	}
}
