package com.progmasters.auti.repository;

import com.progmasters.auti.domain.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {
    List<Institution> findByNameContainsIgnoreCase(String name);
}
