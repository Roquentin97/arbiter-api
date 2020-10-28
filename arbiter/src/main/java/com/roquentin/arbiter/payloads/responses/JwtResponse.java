package com.roquentin.arbiter.payloads.responses;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private String email;
	
	protected JwtResponse(String accessToken, Long id, String username, String email) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.email = email;
	}
	
	public String getToken() {
		return token;
	}
	
	public String getType() {
		return type;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getEmail() {
		return email;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (! (obj instanceof JwtResponse))
			return false;
		JwtResponse response = (JwtResponse) obj;
		return this.token.equals(response.token)
				&& this.id == response.id
				&& this.username.equals(response.username)
				&& this.email.equals(email);
	}
	
	@Override
	public int hashCode() {
		return  id.hashCode() * token.hashCode() + username.hashCode() + email.hashCode(); 
	}
}
