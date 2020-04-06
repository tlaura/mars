package com.progmasters.mars.account_institution.connector.domain;

import com.progmasters.mars.account_institution.account.domain.ProviderAccount;
import com.progmasters.mars.account_institution.institution.domain.Institution;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "account_institution")
@Getter
@NoArgsConstructor
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

    public AccountInstitutionConnector(ProviderAccount providerAccount, Institution institution) {
        this.providerAccount = providerAccount;
        this.institution = institution;
    }
}
