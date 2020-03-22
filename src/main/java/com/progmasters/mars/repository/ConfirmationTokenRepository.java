package com.progmasters.mars.repository;

import com.progmasters.mars.domain.ConfirmationToken;
import com.progmasters.mars.domain.ProviderAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    @Query("select c from ConfirmationToken c where c.token= :token")
    ConfirmationToken findByToken(@Param("token") String token);

    @Query("select c from ConfirmationToken c where c.user= :user")
    Optional<ConfirmationToken> findByUser(@Param("user") ProviderAccount user);


}
