package com.progmasters.mars.account_institution.institution.repository;

import com.progmasters.mars.account_institution.account.domain.ProviderAccount;
import com.progmasters.mars.account_institution.account.domain.ProviderType;
import com.progmasters.mars.account_institution.institution.domain.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Long> {


    List<Institution> findAllByName(@NotBlank @NotEmpty String name);

    List<Institution> findAllByEmail(@NotBlank @NotEmpty @Email String email);

    Institution findByName(String name);

    @Query("select i from Institution i join i.accountInstitutionConnectors ac where ac.providerAccount= :providerAccount")
    List<Institution> getInstitutionsByAccount(@Param("providerAccount") ProviderAccount providerAccount);

    @Query("select i from Institution i where i.accountInstitutionConnectors.size = 0")
    List<Institution> findInstitutionsWithoutProvider();

    @Query("select distinct i from Institution i where i.accountInstitutionConnectors.size > 0")
    List<Institution> findInstitutionsWithProvider();

    @Query("select i from Institution i join i.accountInstitutionConnectors ac join ac.providerAccount p join p.types t where t= :type")
    List<Institution> findInstitutionsByProviderType(@Param("type") ProviderType providerType);
}
