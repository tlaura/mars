package com.progmasters.mars.security;

import com.progmasters.mars.repository.IndividualUserRepository;
import com.progmasters.mars.repository.InstitutionalUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JPAUserDetailsService implements UserDetailsService {

    private InstitutionalUserRepository institutionalUserRepository;
    private IndividualUserRepository individualUserRepository;

    @Autowired
    public JPAUserDetailsService(IndividualUserRepository userRepository, InstitutionalUserRepository institutionalUserRepository) {
        this.institutionalUserRepository = institutionalUserRepository;
        this.individualUserRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.progmasters.mars.domain.User user = individualUserRepository.findByUserName(username);
        if (user == null) {
            user = institutionalUserRepository.findByUserName(username);
        }
        if (user == null) {
            throw new UsernameNotFoundException("No account was found with given name");
        }

        // TODO - set roles for inst and individual users at registration
        List<GrantedAuthority> authorities = AuthorityUtils
                .createAuthorityList(user.getRole().toString());

        UserDetails principal = User
                .withUsername(username)
                .authorities(authorities)
                .password(user.getPassword())
                .build();
        return principal;
    }
}
