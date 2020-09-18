package com.roquentin.arbiter.payloads.requests;

public class LoginRequest {
	private String identifier;
	private String password;
	
	public LoginRequest(String username, String password) {
		this.identifier = username;
		this.password = password;
	}
	
	public String getIdentifier() {return identifier;}
	public String getPassword() {return password;}
}
