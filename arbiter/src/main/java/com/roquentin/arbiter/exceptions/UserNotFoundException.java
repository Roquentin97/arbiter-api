package com.roquentin.arbiter.exceptions;

public class UserNotFoundException extends EntityNotFoundException{
	
	private static final long serialVersionUID = 1;
	
	public UserNotFoundException() {
		super("User not found!");
	}
	
	public UserNotFoundException(String message) {
		super(message);
	}
	
	
}
