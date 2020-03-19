package com.progmasters.mars.config;

import com.progmasters.mars.security.JPAUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${cors-policies}")
    private String[] corsPolicies;
    private JPAUserDetailsService jpaUserDetailsService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(JPAUserDetailsService jpaUserDetailsService, PasswordEncoder passwordEncoder) {
        super();
        this.jpaUserDetailsService = jpaUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jpaUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //TODO Ezt a részt lehet kicsit rendezettebben tartani. PL
        // Van egy speciális komment, ami kikapcsolja a formázást adott szakaszon belül
        // Ezt IDEA-ban külön be kell állítani. Majd itt indentálással, sorbarendezéssel stb
        // kicsit átláthatóbbá lehet tenni a kódot! Ez amúgy elég bevett dolog securityConfignál...
        // MÁSIK, ha egyelőre úgyis minden permitAll() akkor lehet használni wild-cardokat (*, **)
        // Majd pl  .antMatchers(HttpMethod.GET, "/api/**").permitAll() ->Tehát mindent le lehet kérni
        // Majd pl  .antMatchers(HttpMethod.PUT, "/api/**").authenticated ->Tehát mindenki tud PUT-olni aki be van jelentkezve
        // Majd pl  .antMatchers(HttpMethod.POST, "/api/**").hasAnyRole('USER','ADMIN') ->Csak USER vagy ADMIN tud létrehozni
        // Majd pl  .antMatchers(HttpMethod.DELETE, "/api/**").hasRole('ADMIN') -> Csak ADMIN tud törölni BÁRMIT

        // @formatter:off
        http
                //TODO Ez nem kell (: ez nem azt csinálja, amire szerintem gondoltok
//                .formLogin().loginPage("/login")
//                .usernameParameter("email")
//                .permitAll().and()
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/api/user/login").permitAll()
                    .antMatchers("/api/providers/*").permitAll()
                    .antMatchers("/api/institutions/**").permitAll()
                    .antMatchers("/api/institutions/institutionType").permitAll()
                    .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
//                .antMatchers("/api/*").hasRole("ADMIN")
                .and()
                    .logout().deleteCookies("JSESSIONID")
                .and().httpBasic()
        ;
        // @formatter:on

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(corsPolicies));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type", "X-Requested-With"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
