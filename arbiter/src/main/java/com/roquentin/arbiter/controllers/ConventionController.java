package com.roquentin.arbiter.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.roquentin.arbiter.dto.ConventionDTO;
import com.roquentin.arbiter.models.Convention;
import com.roquentin.arbiter.services.ConventionService;



@RestController
@RequestMapping("/api/convention")
public class ConventionController {
	
	@Autowired
	private ConventionService service;
	
	@PostMapping("/create")
	// TODO after voting implementation change to 202 ACCEPTED
	@ResponseStatus(code = HttpStatus.CREATED)
	public Convention createConvention( @Valid @RequestBody ConventionDTO newConvention) {
		return  service.createConvention(newConvention);
	}
	
	@DeleteMapping("/delete/{id}")
	public void deleteConvention(@PathVariable Long id) {
		service.deleteConvention(id);
	}
	
}
