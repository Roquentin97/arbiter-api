package com.roquentin.arbiter.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.roquentin.arbiter.models.Invitation;
import com.roquentin.arbiter.repositories.InvitationRepository;

@Service
public class InvitationService {

	@Autowired
	private InvitationRepository repository;
	
	public Long getCooperationId(String code) {
		return Long.parseLong(code, code.indexOf("xs")+2, code.length(), 16);
	}
	
	public String createInvitation(Long userId, Long cooperationId) {
		StringBuilder sb = new StringBuilder(Long.toHexString(userId))
				.append("sx")
				.append((int)(Math.random() * 350))
				.append("xs")
				.append(Long.toHexString(cooperationId));
		
		Invitation invitation = new Invitation(sb.toString());
		repository.save(invitation);
		return sb.toString();
	}
	
	public Invitation find(String code) {
		return repository.findByCode(code);
	}
	
	
}
