package com.roquentin.arbiter.repositories;

import java.util.Optional;

import org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyProperties.Identityprovider.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.roquentin.arbiter.models.RegistrationVerificationToken;
import com.roquentin.arbiter.models.User;

@Repository
public interface RegistrationVerificationTokenRepository 
	extends JpaRepository<RegistrationVerificationToken, Long> {
	
	RegistrationVerificationToken findByToken(String token);
	
	RegistrationVerificationToken findByUser(User user);
}
