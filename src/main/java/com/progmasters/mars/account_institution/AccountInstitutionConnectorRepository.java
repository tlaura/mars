package com.progmasters.mars.account_institution;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountInstitutionConnectorRepository extends JpaRepository<AccountInstitutionConnector, Long> {
}
