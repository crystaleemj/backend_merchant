package com.crystal.merchant_backend.dto;

import lombok.Data;

@Data
public class UserCreationRequest {
    private String username;
    private String password;
    private String email;
}
