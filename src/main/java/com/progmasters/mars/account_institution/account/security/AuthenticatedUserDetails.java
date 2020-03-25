package com.progmasters.mars.account_institution.account.security;

import com.progmasters.mars.account_institution.account.domain.ProviderAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

public class AuthenticatedUserDetails {

    private String fullNameOfUser;
    private String name; //Email
    private String role;

//    public AuthenticatedUserDetails(UserDetails userDetails) {
//        this.name = userDetails.getUsername();
//        this.role = findRole(userDetails);
//    }

    public AuthenticatedUserDetails(ProviderAccount providerAccount) {
        this.fullNameOfUser = providerAccount.getName();
        this.name = providerAccount.getEmail();
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

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getFullNameOfUser() {
        return fullNameOfUser;
    }

    public AuthenticatedUserDetails() {
    }
}
