package com.roquentin.arbiter.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roquentin.arbiter.models.Cooperation;
import com.roquentin.arbiter.models.User;

@Repository
public interface CooperationRepository extends JpaRepository<Cooperation, Long> {
	Set<Cooperation>findByUsers(User user);
}
