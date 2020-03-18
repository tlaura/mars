package com.progmasters.mars.repository;

import com.progmasters.mars.domain.InstitutionType;
import com.progmasters.mars.domain.ProviderAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProviderAccountRepository extends JpaRepository<ProviderAccount, Long> {

    List<ProviderAccount> findAllByEmail(String email);

    ProviderAccount findByEmail(String username);

    List<ProviderAccount> findAllByName(String name);

    @Query("select p from ProviderAccount p join p.type t where t= :type")
    List<ProviderAccount> findByType(@Param("type") InstitutionType institutionType);
}
