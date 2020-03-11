package com.progmasters.mars.repository;

import com.progmasters.mars.domain.ProviderAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProviderAccountRepository extends JpaRepository<ProviderAccount, Long> {

}
