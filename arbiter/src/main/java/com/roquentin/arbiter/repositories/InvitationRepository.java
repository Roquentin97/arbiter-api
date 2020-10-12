package com.roquentin.arbiter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roquentin.arbiter.models.Invitation;


@Repository
public interface InvitationRepository extends JpaRepository<Invitation, String > {
	Invitation findByCode(String code);
}
