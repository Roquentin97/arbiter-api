package com.roquentin.arbiter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roquentin.arbiter.models.Invitation;
import com.roquentin.arbiter.models.UserCooperation;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, UserCooperation> {
	Invitation findByCode(String code);
}
