package com.roquentin.arbiter.expections;

import java.util.Map;

public class UniqueKeyViolationException extends RuntimeException {
	
	public UniqueKeyViolationException() {
		super("Some of given values that must be unique already in use");
	}
	
	public UniqueKeyViolationException(Map<String, String> errors) {
		super("Next fields must be unique but already in use:\n   " + errors.toString());
	}

}
