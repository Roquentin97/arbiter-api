package com.roquentin.arbiter.services;


import java.util.Map;
import java.util.Set;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.roquentin.arbiter.expections.CooperationNotFoundException;
import com.roquentin.arbiter.models.Cooperation;
import com.roquentin.arbiter.models.User;
import com.roquentin.arbiter.payloads.requests.LoginRequest;
import com.roquentin.arbiter.repositories.CooperationRepository;

@Service
public class CooperationService {
	
	@Autowired
	private CooperationRepository repository;
	
	@Autowired
	private UserService userService;
	
	@Autowired 
	InvitationService invitationService;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Transactional
	public Map<String, String> createCooperation(Cooperation newCooperation) {
	
		newCooperation.setUsers(Set.of(userService.getCurrentUser()));
		newCooperation.setPassword(encoder.encode(newCooperation.getPassword()));
		newCooperation = repository.save(newCooperation);
		return Map.of("invitation", invitationService.createInvitation(userService.getCurrentUser().getId(), newCooperation.getId()));
	}
	
	public Set<Cooperation> getUsersCooperations(){
		return repository.findByUsers(userService.getCurrentUser());
	}
	
	public void leaveCooperation(Long id){
		Cooperation coop = repository.findById(id).orElseThrow(CooperationNotFoundException::new);
		var users = coop.getUsers();
		
		User user = userService.getCurrentUser();
		//TODO log dubious action
		if (! users.contains(user)) 
				throw new CooperationNotFoundException();
		
		if (users.size() == 1) {
			repository.delete(coop);
			return;
		}
		
		users.remove(user);
		
		coop.setUsers(users);
		coop = repository.save(coop);
		
	}
	
	public ResponseEntity<?> joinViaInvitation(LoginRequest request) {
		Long id = invitationService.getCooperationId(request.getIdentifier());
		Cooperation cooperation = repository.getOne(id);
		
		if (encoder.matches(request.getPassword(), cooperation.getPassword())) {
			cooperation.getUsers().add(userService.getCurrentUser());

			//TODO: notify in coop chat
			cooperation = repository.save(cooperation);
			return ResponseEntity.ok().body(cooperation);
		}
		
		return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}
	
	public boolean canUserChangeCooperation(Long cooperationId, User user) {
		return canUserChangeCooperation(repository.getOne(cooperationId), user);
	}
	
	public boolean canUserChangeCooperation(Cooperation cooperation, User user) {
		return cooperation.getUsers().contains(user);
	}

}
