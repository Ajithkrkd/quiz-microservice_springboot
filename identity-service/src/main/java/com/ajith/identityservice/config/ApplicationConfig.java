package com.ajith.identityservice.config;

import com.ajith.identityservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository userRepository;

    @Bean
    public PasswordEncoder passwordEncoder ( ) {
        return new BCryptPasswordEncoder ( );
    }

    @Bean
    public UserDetailsService userDetailsService ( ) {
        return username -> userRepository.findByEmail ( username )
                .orElseThrow ( ( ) -> new UsernameNotFoundException ( "user not fount" ) );
    }

    @Bean
    public AuthenticationManager authenticationManager
            (AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager ( );
    }


    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider ( );
        provider.setPasswordEncoder ( passwordEncoder () );
        provider.setUserDetailsService ( userDetailsService () );
        return provider;
    }
}