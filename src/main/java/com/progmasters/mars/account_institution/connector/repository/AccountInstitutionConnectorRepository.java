package com.progmasters.mars.account_institution.connector.repository;

import com.progmasters.mars.account_institution.account.domain.ProviderAccount;
import com.progmasters.mars.account_institution.connector.domain.AccountInstitutionConnector;
import com.progmasters.mars.account_institution.institution.domain.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AccountInstitutionConnectorRepository extends JpaRepository<AccountInstitutionConnector, Long> {

    @Transactional
    @Modifying
    @Query("delete from AccountInstitutionConnector c where c.providerAccount= :providerAccount and c.institution= :institution")
    void removeConnection(@Param("providerAccount") ProviderAccount providerAccount, @Param("institution") Institution institution);

    void removeAllByProviderAccountId(Long id);

    @Query("select c from AccountInstitutionConnector c where c.providerAccount= :providerAccount and c.institution= :institution")
    AccountInstitutionConnector findByAccounts(@Param("providerAccount") ProviderAccount providerAccount, @Param("institution") Institution institution);

}
