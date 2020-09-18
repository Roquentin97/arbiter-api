package com.roquentin.arbiter.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.roquentin.arbiter.models.Role;
import com.roquentin.arbiter.models.User;

import lombok.Data;

@Data
public class UserDetailsImpl implements UserDetails{
	
	private static final long SerialVersionUID = 1L;
	
	private Long id;
	
	private String username;
	
	private String email;
	
	@JsonIgnore 
	private String password;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserDetailsImpl(Long id, String username, String email, String password, Set<Role> roles) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		
		var _authorities = new ArrayList<GrantedAuthority>();
		roles.forEach(
				role -> _authorities.add(new SimpleGrantedAuthority(role.getName())));
		
		authorities = _authorities;
	}
	
	public static UserDetailsImpl build(User user) {
		return new UserDetailsImpl(
				user.getId(), 
				user.getUsername(),
				user.getEmail(), 
				user.getPassword(),
				user.getRoles());
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
}
