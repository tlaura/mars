package com.progmasters.mars.account_institution.account.security;

import com.progmasters.mars.account_institution.account.domain.ProviderAccount;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class AuthenticatedUserDetails {

    private String fullNameOfUser;
    private String email; //Email
    private String role;

    public AuthenticatedUserDetails(ProviderAccount providerAccount) {
        this.fullNameOfUser = providerAccount.getName();
        this.email = providerAccount.getEmail();
        this.role = providerAccount.getRole().name();
    }

    private String findRole(UserDetails userDetails) {
        String role = null;
        List<GrantedAuthority> roles = userDetails.getAuthorities().stream()
                .filter(authority -> authority.getAuthority().startsWith("ROLE_"))
                .collect(Collectors.toList());
        if (!roles.isEmpty()) {
            role = roles.get(0).getAuthority();
        }
        return role;
    }
}
