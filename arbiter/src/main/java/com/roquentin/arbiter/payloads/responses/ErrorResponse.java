package com.roquentin.arbiter.payloads.responses;

public class ErrorResponse<T> {
	private Causes cause;
	private T message;
	
	public enum Causes {
		INTEGRITY_EXCEPTION, ARGUMENT_NOT_VALID
	}
	
	protected ErrorResponse(Causes cause, T message ) {
		this.cause = cause;
		this.message = message;
	}
	
	public String getCause() { return cause.name();}
	public T getMessage() {return message;}
}
