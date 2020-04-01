package com.progmasters.mars.chat.dto;

import com.progmasters.mars.account_institution.account.domain.ProviderAccount;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ContactsData {
    private String providerName;

    private String name;

    private String email;

    public ContactsData(ProviderAccount providerAccount) {
        this.providerName = providerAccount.getProviderServiceName();
        this.name = providerAccount.getName();
        this.email = providerAccount.getEmail();
    }
}
