package com.DevSec.Devops.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
