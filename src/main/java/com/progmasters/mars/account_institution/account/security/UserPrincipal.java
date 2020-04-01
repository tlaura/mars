package com.progmasters.mars.account_institution.account.security;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.progmasters.mars.account_institution.account.domain.User;
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

    private String role;

    @JsonIgnore  // ignore during serialization
    private String password;

    public UserPrincipal(String username, String email, String password, String role, Collection<? extends GrantedAuthority> authorities) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities = AuthorityUtils
                .createAuthorityList(user.getRole().toString());

        return new UserPrincipal(
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().name(),
                authorities
        );
    }

    public String getRole() {
        return role;
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
        return true;
    }
}
