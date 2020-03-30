package com.progmasters.mars.account_institution.account.security;

import com.progmasters.mars.account_institution.account.domain.ProviderAccount;
import com.progmasters.mars.account_institution.account.repository.ProviderAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        com.progmasters.mars.domain.User user = individualUserRepository.findByEmail(username);
        ProviderAccount providerAccount = providerAccountRepository.findByEmail(username).orElseThrow(() -> new EntityNotFoundException("No account found"));


        List<GrantedAuthority> authorities = AuthorityUtils
                .createAuthorityList(providerAccount.getRole().toString());

        return UserPrincipal.create(providerAccount);

//        return User
//                .withUsername(username)
//                .authorities(authorities)
//                .password(providerAccount.getPassword())
//                .build();
    }

}
