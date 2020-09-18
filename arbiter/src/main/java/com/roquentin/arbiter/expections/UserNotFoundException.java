package com.roquentin.arbiter.expections;

public class UserNotFoundException extends EntityNotFoundException{
	
	public UserNotFoundException() {
		super("User not found!");
	}
	
	public UserNotFoundException(String message) {
		super(message);
	}
	
	
}
