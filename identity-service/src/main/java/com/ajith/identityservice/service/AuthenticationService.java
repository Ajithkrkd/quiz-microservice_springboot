package com.ajith.identityservice.service;


import com.ajith.identityservice.common.BasicResponse;
import com.ajith.identityservice.dto.*;
import com.ajith.identityservice.entity.UserEntity;
import com.ajith.identityservice.enums.Role;
import com.ajith.identityservice.exceptions.EmailAlreadyExistsException;
import com.ajith.identityservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private  final AuthenticationManager authenticationManager;

    public ResponseEntity < BasicResponse > register (RegisterRequest request) {
        try {
            boolean isEmailExist = userRepository.existsByEmail ( request.getEmail ( ) );
            if ( isEmailExist ) {
                throw new EmailAlreadyExistsException ( "email already exists with email " + request.getEmail ( ) );
            }
            UserEntity newUser = buildUser ( request );
            userRepository.save ( newUser );
            return ResponseEntity.status ( HttpStatus.CREATED )
                    .body ( BasicResponse.builder ( )
                            .status ( HttpStatus.CREATED.value ( ) )
                            .description ( "new user created successfully" )
                            .message ( "successfully registered" )
                            .timestamp ( LocalDateTime.now ( ) )
                            .build ( ) );
        } catch (EmailAlreadyExistsException e) {
            throw new RuntimeException ( e );
        } catch (Exception e) {
            throw new RuntimeException ( "server error" );
        }

    }

    private UserEntity buildUser (RegisterRequest request) {
        return UserEntity.builder ( )
                .email ( request.getEmail ( ) )
                .role ( Role.ADMIN )
                .password ( passwordEncoder.encode ( request.getPassword ( ) ) )
                .phoneNumber ( request.getPhoneNumber ( ) )
                .joinDateTime ( LocalDateTime.now () )
                .build ( );
    }

    public ResponseEntity < AuthenticationResponse > login (LoginRequest request) {


        try {
            authenticationManager.authenticate (
            new UsernamePasswordAuthenticationToken (
                    request.getEmail (),
                    request.getPassword ()

            )
            );
            var user = userRepository.findByEmail ( request.getEmail ( ) ).orElseThrow (()->new UsernameNotFoundException ( "user is not there" ));
            String jwtToken = jwtService.generateToken ( user );
            return ResponseEntity.status ( HttpStatus.OK).body ( AuthenticationResponse
                    .builder ( )
                    .message ( "Success you are authenticated" )
                    .access_token ( jwtToken )
                    .build ( ) );

        }catch (BadCredentialsException e) {
            throw new BadCredentialsException ("Password is Wrong");
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("Worker not found");
        }




    }

    public ResponseEntity< BasicResponse> updateUser (String id,UpdateRequest request) {
        Optional < UserEntity > optionalUser = userRepository.findById (Long.valueOf ( id ));
        if ( optionalUser.isPresent ( ) ) {
                UserEntity existingUser = optionalUser.get ( );
                existingUser.setEmail ( request.getEmail ()  );
                existingUser.setPassword ( passwordEncoder.encode ( request.getPassword() ));
                existingUser.setJoinDateTime ( LocalDateTime.now (  ) );
                existingUser.setPhoneNumber ( request.getPhoneNumber() );
                userRepository.save ( existingUser );
                return ResponseEntity.status ( HttpStatus.OK).body ( BasicResponse
                .builder ( )
                .message ( "successfully authenticated" )
                                .timestamp ( LocalDateTime.now () )
                                .description ( "user updated with email"+ request.getEmail () )
                                .status ( HttpStatus.OK.value ( ) )
                .build ( ) );
        }
        return ResponseEntity.status ( HttpStatus.NO_CONTENT).body ( BasicResponse
                .builder ( )
                .message ( "email not font" )
                .timestamp ( LocalDateTime.now () )
                .description ( "user updated with email"+ request.getEmail () )
                .status ( HttpStatus.OK.value ( ) )
                .build ( ) );
    }

    public List< UserDetailsDto> getAllUsers ( ) {
            List<UserEntity> products = userRepository.findAll ();
            return    products.stream ().map ( this::mapToUserDto ).toList ();
        }
    private UserDetailsDto mapToUserDto (UserEntity userEntity) {
      return   UserDetailsDto.builder ()
                .id ( userEntity.getId () )
                .email ( userEntity.getEmail () )
                .phoneNumber ( userEntity.getPhoneNumber () )
                .joinDateTime ( userEntity.getJoinDateTime () )
                .build ();
    }
}
