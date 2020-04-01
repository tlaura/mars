package com.progmasters.mars.chat.dto;

import com.progmasters.mars.account_institution.account.domain.ProviderAccount;
import com.progmasters.mars.account_institution.account.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ContactsData {
    private String providerName;

    private String name;

    private String email;

    public ContactsData(User user) {
        if (user instanceof ProviderAccount) {
            ProviderAccount providerAccount = (ProviderAccount) user;
            this.providerName = providerAccount.getProviderServiceName();
        }
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
