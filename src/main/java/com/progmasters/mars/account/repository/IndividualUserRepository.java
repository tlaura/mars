package com.progmasters.mars.account.repository;

import com.progmasters.mars.account.domain.IndividualUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualUserRepository extends JpaRepository<IndividualUser, Long> {
}
