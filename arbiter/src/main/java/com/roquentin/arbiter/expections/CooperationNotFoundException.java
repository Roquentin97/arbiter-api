package com.roquentin.arbiter.expections;

public class CooperationNotFoundException extends EntityNotFoundException {
	public CooperationNotFoundException() {
		super("Cooperation not found!");
	}
	
	public CooperationNotFoundException(String message) {
		super(message);
	}
}
