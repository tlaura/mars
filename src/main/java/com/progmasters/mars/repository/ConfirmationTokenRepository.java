package com.progmasters.mars.repository;

import com.progmasters.mars.domain.ConfirmationToken;
import com.progmasters.mars.domain.IndividualUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    ConfirmationToken findByToken(String token);

}
