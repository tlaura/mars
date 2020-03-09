package com.progmasters.mars.repository;

import com.progmasters.mars.domain.IndividualUser;
import com.progmasters.mars.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndividualUserRepository extends JpaRepository<IndividualUser, Long> {
    User findByUserName(String username);
}
