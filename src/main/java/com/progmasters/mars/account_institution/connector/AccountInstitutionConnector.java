package com.progmasters.mars.account_institution.connector;

import com.progmasters.mars.account_institution.account.domain.ProviderAccount;
import com.progmasters.mars.account_institution.institution.domain.Institution;

import javax.persistence.*;

@Entity
@Table(name = "account_institution")
public class AccountInstitutionConnector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "provider_account_id")
    private ProviderAccount providerAccount;

    @ManyToOne
    @JoinColumn(name = "institution_id")
    private Institution institution;

    public AccountInstitutionConnector() {
    }

    public AccountInstitutionConnector(ProviderAccount providerAccount, Institution institution) {

        this.providerAccount = providerAccount;
        this.institution = institution;
    }

    public Long getId() {
        return id;
    }

    public ProviderAccount getProviderAccount() {
        return providerAccount;
    }

    public Institution getInstitution() {
        return institution;
    }
}
