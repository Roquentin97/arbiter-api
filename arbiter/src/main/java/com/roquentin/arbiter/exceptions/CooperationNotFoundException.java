package com.roquentin.arbiter.exceptions;

public class CooperationNotFoundException extends EntityNotFoundException {
	private static final long serialVersionUID = 1;
	
	public CooperationNotFoundException() {
		super("Cooperation not found!");
	}
	
	public CooperationNotFoundException(String message) {
		super(message);
	}
}
