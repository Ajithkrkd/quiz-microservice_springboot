package com.ajith.identityservice.controller;

import com.ajith.identityservice.common.BasicResponse;
import com.ajith.identityservice.dto.AuthenticationResponse;
import com.ajith.identityservice.dto.LoginRequest;
import com.ajith.identityservice.dto.RegisterRequest;
import com.ajith.identityservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity< BasicResponse > register( @RequestBody  RegisterRequest request)
    {
        return authenticationService.register ( request );
    }

    @PostMapping("/login")
    public ResponseEntity< AuthenticationResponse > login(@RequestBody LoginRequest request){
        return authenticationService.login ( request );
    }


}
