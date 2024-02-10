package com.ajith.identityservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto {

    private Long id;
    private String email;
    private String phoneNumber;
    private LocalDateTime joinDateTime;
}
