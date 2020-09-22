package com.roquentin.arbiter.expections;

public class UnauthorizedException extends RuntimeException{
	public UnauthorizedException() {
		super("Invalid login or password");
	}
	
	public UnauthorizedException(String message) {
		super(message);
	}
	
}
