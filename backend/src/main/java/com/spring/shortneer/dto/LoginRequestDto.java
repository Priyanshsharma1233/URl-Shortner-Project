package com.spring.shortneer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
