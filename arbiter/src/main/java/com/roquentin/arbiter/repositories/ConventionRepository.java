package com.roquentin.arbiter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.roquentin.arbiter.dto.ConventionDTO;
import com.roquentin.arbiter.models.Convention;

@Repository
public interface ConventionRepository  extends JpaRepository<Convention, Long>{
	@Modifying(flushAutomatically = true)
	@Query(value = "INSERT INTO conventions (name, description, cooperation_id, consequence) VALUES (:name, :desc, :coop, :cons)", nativeQuery = true)
	Convention saveUsingDTO(@Param("name") String name, @Param("desc") String description,
			@Param("coop") Long cooperationId, @Param("cons") String consequence);
}
