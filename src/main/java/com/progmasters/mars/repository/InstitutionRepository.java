package com.progmasters.mars.repository;

import com.progmasters.mars.domain.Institution;
import com.progmasters.mars.domain.InstitutionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {
    List<Institution> findByNameContainsIgnoreCase(String name);

    List<Institution> findAllByInstitutionType(InstitutionType institutionType);

}
