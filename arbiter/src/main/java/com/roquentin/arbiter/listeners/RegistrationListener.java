package com.roquentin.arbiter.listeners;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.roquentin.arbiter.events.OnRegistrationCompleteEvent;
import com.roquentin.arbiter.models.User;
import com.roquentin.arbiter.services.UserService;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
	
	@Autowired
	private UserService userService;
	
	/*
	 * TODO: uncomment when adding localization 
	@Autowired
	private MessageSource messages;
	*/
	
	/*
	 * TODO add mail sending implementation
	@Autowired
	private JavaMailSender sender;
	*/
	
	@Override
	public void onApplicationEvent(OnRegistrationCompleteEvent event) {
		sendRegistrationConfirmation(event);
	}
	
	private void sendRegistrationConfirmation(OnRegistrationCompleteEvent event) {
		User user = event.getUser();
		String token = UUID.randomUUID().toString();
		userService.createVerificationToken(user, token);
		
		String recipientAddress = user.getEmail();
		
		//TODO: localize message
		String subject = "Registration confirmation";
		
		//TODO get address from properties
		String confirmationUrl = "/api/auth/confirmRegistration?token=" + token;
		String message = "To complete registration follow next link";
		
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipientAddress);
		email.setSubject(subject);
		
		// TODO: get from properties
		email.setText(message + "\r\n" + "http://localhost:8080" + confirmationUrl);
		
		//TODO send as email, don't print
		System.out.println("SENT: " + email);
		//sender.send(email);
		
		
	}
}
