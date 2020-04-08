package com.progmasters.mars.account_institution.account.passwordtoken;

import com.progmasters.mars.account_institution.account.domain.ProviderAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PasswordTokenRepository extends JpaRepository<PasswordToken, Long> {

    @Query("select p from PasswordToken p where p.token= :token")
    Optional<PasswordToken> findByToken(@Param("token") String token);

    @Query("select p from PasswordToken p where p.user= :user")
    Optional<PasswordToken> findByUser(@Param("user") ProviderAccount user);

    @Query("select p from PasswordToken p")
    List<PasswordToken> findAllPasswordTokens();
}
