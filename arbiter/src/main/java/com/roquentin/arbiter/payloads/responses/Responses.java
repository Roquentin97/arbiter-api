package com.roquentin.arbiter.payloads.responses;

import org.springframework.security.core.userdetails.UserDetails;

import com.roquentin.arbiter.payloads.responses.ErrorResponse.Causes;
import com.roquentin.arbiter.security.UserDetailsImpl;

public class Responses {
	private Responses() {}
	
	public static <T> ErrorResponse<T> errorResponse(Causes cause, T message) {
		return new ErrorResponse<>(cause, message);
	}
	
	public static JwtResponse jwtResponse(String token, UserDetailsImpl details) {
		return new JwtResponse(token, details.getId(), details.getUsername(), details.getEmail());
	}
}
