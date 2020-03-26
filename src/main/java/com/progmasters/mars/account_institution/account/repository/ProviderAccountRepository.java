package com.progmasters.mars.account_institution.account.repository;

import com.progmasters.mars.account_institution.account.domain.ProviderAccount;
import com.progmasters.mars.account_institution.account.domain.ProviderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProviderAccountRepository extends JpaRepository<ProviderAccount, Long> {

    List<ProviderAccount> findAllByEmail(String email);

    Optional<ProviderAccount> findByEmail(String email);

    @Query("select p from ProviderAccount p join p.types t where t= :type")
    List<ProviderAccount> findByType(@Param("type") ProviderType providerType);

    @Query("select p from ProviderAccount p where p.accountInstitutionConnectors.size= 0")
    List<ProviderAccount> findProviderAccountWithoutInstitution();

    @Query("select distinct p from ProviderAccount p where p.accountInstitutionConnectors.size > 0")
    List<ProviderAccount> findProviderAccountsWithInstitutions();

    @Query("select p from ProviderAccount p where p.ageGroupMin >= :min and p.ageGroupMax <= :max")
    List<ProviderAccount> findProviderAccountsByAgeRange(@Param("min") Integer min, @Param("max") Integer max);
}