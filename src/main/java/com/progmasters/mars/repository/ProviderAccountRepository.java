package com.progmasters.mars.repository;

import com.progmasters.mars.domain.ProviderAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProviderAccountRepository extends JpaRepository<ProviderAccount, Long> {
    List<ProviderAccount> findAllByUsername(String username);

    List<ProviderAccount> findAllByEmail(String email);

    ProviderAccount findByEmail(String username);
}
