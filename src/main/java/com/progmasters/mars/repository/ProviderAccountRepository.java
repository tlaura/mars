package com.progmasters.mars.repository;

import com.progmasters.mars.domain.InstitutionType;
import com.progmasters.mars.domain.ProviderAccount;
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

    List<ProviderAccount> findAllByName(String name);

    @Query("select p from ProviderAccount p join p.types t where t= :type")
    List<ProviderAccount> findByType(@Param("type") InstitutionType institutionType);
}
