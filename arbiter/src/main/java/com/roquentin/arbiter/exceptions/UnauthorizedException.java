package com.roquentin.arbiter.exceptions;

public class UnauthorizedException extends RuntimeException{
	
	private static final long serialVersionUID = 1;
	
	public UnauthorizedException() {
		super("Invalid login or password");
	}
	
	public UnauthorizedException(String message) {
		super(message);
	}
	
}
