package com.progmasters.mars.account.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

public class AuthenticatedUserDetails {
    private String name;

    private String role;

    public AuthenticatedUserDetails(UserDetails userDetails) {
        this.name = userDetails.getUsername();
        this.role = findRole(userDetails);
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

    public AuthenticatedUserDetails() {
    }
}
