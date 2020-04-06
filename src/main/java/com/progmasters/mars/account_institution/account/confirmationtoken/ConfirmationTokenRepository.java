package com.progmasters.mars.account_institution.account.confirmationtoken;

import com.progmasters.mars.account_institution.account.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    @Query("select c from ConfirmationToken c where c.token= :token")
    ConfirmationToken findByToken(@Param("token") String token);

    @Query("select c from ConfirmationToken c where c.user= :user")
    ConfirmationToken findByUser(@Param("user") User user);
}
