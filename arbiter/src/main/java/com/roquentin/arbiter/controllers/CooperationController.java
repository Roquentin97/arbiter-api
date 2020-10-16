package com.roquentin.arbiter.controllers;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.roquentin.arbiter.dto.CooperationFullDTO;
import com.roquentin.arbiter.dto.CooperationRefDTO;
import com.roquentin.arbiter.models.Cooperation;
import com.roquentin.arbiter.payloads.requests.LoginRequest;
import com.roquentin.arbiter.services.CooperationService;

@RestController
@RequestMapping("/api/cooperation")
public class CooperationController {
	
	@Autowired
	private CooperationService service;
	
	@PostMapping("/create")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Map<String, String> createCooperation(@Valid @RequestBody Cooperation newCooperation ) {
		return service.createCooperation(newCooperation);
	}
	
	@GetMapping("/list")
	@ResponseStatus(HttpStatus.OK)
	public Set<CooperationRefDTO> getAllForUser(){
		return service.getUsersCooperations();
	}
	
	@GetMapping("/leave/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void leaveCooperation(@PathVariable Long id){
		 service.leaveCooperation(id);
	}
	
	
	@PostMapping("/join")
	public ResponseEntity<?> attendCooperation(@RequestBody LoginRequest request){
		return service.joinViaInvitation(request);
	}
	
	
	
}
