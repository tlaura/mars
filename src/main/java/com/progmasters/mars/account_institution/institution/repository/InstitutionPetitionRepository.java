package com.progmasters.mars.account_institution.institution.repository;

import com.progmasters.mars.account_institution.institution.domain.Institution;
import com.progmasters.mars.account_institution.institution.domain.InstitutionPetition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstitutionPetitionRepository extends JpaRepository<InstitutionPetition, Long> {

    @Query("select i from InstitutionPetition i where i.institution= :institution")
    Optional<InstitutionPetition> findByInstituion(@Param("institution") Institution institution);

}
