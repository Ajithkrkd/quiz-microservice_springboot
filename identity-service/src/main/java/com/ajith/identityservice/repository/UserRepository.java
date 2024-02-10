package com.ajith.identityservice.repository;

import com.ajith.identityservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository< UserEntity , Long > {
    boolean existsByEmail (String email);

    Optional<UserEntity> findByEmail (String email);
}
