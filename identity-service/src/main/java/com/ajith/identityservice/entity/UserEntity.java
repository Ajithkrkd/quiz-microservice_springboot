package com.ajith.identityservice.entity;

import com.ajith.identityservice.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Indexed;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private Long id;
    @Column (unique = true)
    private String email;
    private String password;
    private String phoneNumber;
    private LocalDateTime joinDateTime;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Override
    public Collection < ? extends GrantedAuthority > getAuthorities ( ) {
        return List.of ( new  SimpleGrantedAuthority (role.name()));
    }

    @Override
    public String getPassword ( ) {
        return password;
    }

    @Override
    public String getUsername ( ) {
        return email;
    }

    @Override
    public boolean isAccountNonExpired ( ) {
        return true;
    }

    @Override
    public boolean isAccountNonLocked ( ) {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired ( ) {
        return true;
    }

    @Override
    public boolean isEnabled ( ) {
        return true;
    }


}
