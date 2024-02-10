package com.ajith.identityservice.controller;

import com.ajith.identityservice.common.BasicResponse;
import com.ajith.identityservice.dto.RegisterRequest;
import com.ajith.identityservice.dto.UpdateRequest;
import com.ajith.identityservice.dto.UserDetailsDto;
import com.ajith.identityservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final AuthenticationService authenticationService;

    @PutMapping("/user/update/{id}")
    public ResponseEntity< BasicResponse > updateUser(
            @PathVariable String id,
            @RequestBody UpdateRequest request)
    {
        return authenticationService.updateUser(id,request);
    }

    @GetMapping("/admin/allUsers")
    public List < UserDetailsDto > getAllUsers()
    {
        return authenticationService.getAllUsers();
    }
}
