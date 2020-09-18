package com.roquentin.arbiter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roquentin.arbiter.models.Role;
import com.roquentin.arbiter.repositories.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository repository;
	
	public Role getRoleByName(String name) {
		return repository.findByName(name);
	}

}
