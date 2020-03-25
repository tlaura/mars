package com.progmasters.mars.account_institution;

import com.progmasters.mars.account_institution.account.domain.ProviderAccount;
import com.progmasters.mars.account_institution.institution.domain.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountInstitutionConnectorRepository extends JpaRepository<AccountInstitutionConnector, Long> {

    @Query("delete from AccountInstitutionConnector c where c.providerAccount= :providerAccount and c.institution= :institution")
    void removeConnection(@Param("account") ProviderAccount providerAccount, @Param("institution") Institution institution);
}
