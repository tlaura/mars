package com.progmasters.mars.account_institution.account.security;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.progmasters.mars.account_institution.account.domain.ProviderAccount;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@EqualsAndHashCode
@NoArgsConstructor
public class UserPrincipal implements UserDetails {

    private Collection<? extends GrantedAuthority> authorities;

    private String username;

    private String email;

    @JsonIgnore  // ignore during serialization
    private String password;

    public UserPrincipal(String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.username = email;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal create(ProviderAccount user) {
        List<GrantedAuthority> authorities = AuthorityUtils
                .createAuthorityList(user.getRole().toString());

        return new UserPrincipal(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
