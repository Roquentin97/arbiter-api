package com.roquentin.arbiter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roquentin.arbiter.models.Convention;

@Repository
public interface ConventionRepository  extends JpaRepository<Convention, Long>{

}
