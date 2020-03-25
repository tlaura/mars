package com.progmasters.mars.account_institution.institution.openinghours.repository;

import com.progmasters.mars.account_institution.institution.openinghours.domain.OpeningHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpeningHoursRepository extends JpaRepository<OpeningHours, Long> {


    @Query("select o from OpeningHours o where o.institution.id= :id")
    List<OpeningHours> getOpeningHoursByInstitutionId(@Param("id") Long id);
}
