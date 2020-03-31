package com.progmasters.mars.account_institution.institution.repository;

import com.progmasters.mars.account_institution.institution.domain.ConfirmationInstitution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmationInstitutionRepository extends JpaRepository<ConfirmationInstitution, Long> {

}
