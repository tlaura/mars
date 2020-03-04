package com.progmasters.mars.repository;

import com.progmasters.mars.domain.InstitutionalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstitutionalUserRepository extends JpaRepository<InstitutionalUser, Long> {
}
