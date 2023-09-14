package com.crystal.merchant_backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserAuth {
    private int user_id;
    private String username;
    private String password;
    private String email;
    private int reset;
    private byte[] salt;
}
