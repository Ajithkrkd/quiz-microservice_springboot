package com.ajith.identityservice.config;

import com.ajith.identityservice.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private static final String[] WHITE_LIST_URLS =
            {
              "/api/auth/**"
            };
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.csrf ( AbstractHttpConfigurer::disable )
                .authorizeHttpRequests ( auth -> {
                    auth.requestMatchers (WHITE_LIST_URLS)
                            .permitAll ()
                            .requestMatchers ( "/api/admin/**" ).hasAnyAuthority ( Role.ADMIN.name ( ) )
                            .requestMatchers ( "/api/user/**" ).hasAnyAuthority ( Role.USER.name () )
                            .anyRequest ().authenticated ();
                } )
                .sessionManagement ( session -> session.sessionCreationPolicy ( SessionCreationPolicy.STATELESS ) )
                .authenticationProvider ( authenticationProvider )
                .addFilterBefore ( jwtAuthFilter, UsernamePasswordAuthenticationFilter.class )
                .build ( );
    }
}
