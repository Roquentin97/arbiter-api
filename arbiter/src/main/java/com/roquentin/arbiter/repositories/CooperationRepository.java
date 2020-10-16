package com.roquentin.arbiter.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.roquentin.arbiter.dto.CooperationRefDTO;
import com.roquentin.arbiter.models.Cooperation;
import com.roquentin.arbiter.models.User;

@Repository
public interface CooperationRepository extends JpaRepository<Cooperation, Long> {
	@Query("SELECT new com.roquentin.arbiter.dto.CooperationRefDTO(c.id, c.name, c.description)"
			+ "FROM Cooperation c INNER JOIN c.users cu WHERE cu = :user")
	Set<CooperationRefDTO>findByUsersToRefDTO(@Param("user") User user);
}
