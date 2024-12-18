package com.project.security_bootstarter.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDto {
    String username;
    String password;

    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
