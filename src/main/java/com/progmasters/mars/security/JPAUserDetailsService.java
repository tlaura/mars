package com.progmasters.mars.security;

import com.progmasters.mars.domain.ProviderAccount;
import com.progmasters.mars.repository.ProviderAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class JPAUserDetailsService implements UserDetailsService {

    private ProviderAccountRepository providerAccountRepository;
//    private IndividualUserRepository individualUserRepository;

    @Autowired
    public JPAUserDetailsService(ProviderAccountRepository providerAccountRepository) {
        this.providerAccountRepository = providerAccountRepository;
//        this.individualUserRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        com.progmasters.mars.domain.User user = individualUserRepository.findByEmail(username);
        ProviderAccount providerAccount = providerAccountRepository.findByEmail(username).orElseThrow(() -> new EntityNotFoundException("No account found"));

        if (providerAccount == null) {
            throw new UsernameNotFoundException("No account was found with given name");
        }

        List<GrantedAuthority> authorities = AuthorityUtils
                .createAuthorityList(providerAccount.getRole().toString());

        return User
                .withUsername(username)
                .authorities(authorities)
                .password(providerAccount.getPassword())
                .build();
    }
}
