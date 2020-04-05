package com.progmasters.mars.account_institution.institution.repository;

import com.progmasters.mars.account_institution.institution.domain.ConfirmationInstitution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfirmationInstitutionRepository extends JpaRepository<ConfirmationInstitution, Long> {

    @Query("select i from ConfirmationInstitution i where i.name= :name")
    List<ConfirmationInstitution> findAllByName(@Param("name") String name);
}
