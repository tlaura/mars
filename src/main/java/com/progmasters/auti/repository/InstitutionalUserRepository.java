package com.progmasters.auti.repository;

import com.progmasters.auti.domain.InstitutionalUser;
import com.progmasters.auti.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstitutionalUserRepository extends JpaRepository<InstitutionalUser, Long> {
}
