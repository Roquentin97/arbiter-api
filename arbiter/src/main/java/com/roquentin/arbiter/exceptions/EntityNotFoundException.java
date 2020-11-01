package com.roquentin.arbiter.exceptions;

public class EntityNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1;
	
	public EntityNotFoundException() {
		super ("Entity not found exception");
	}
	
	public EntityNotFoundException(String message) {
		super(message);
	}
}
