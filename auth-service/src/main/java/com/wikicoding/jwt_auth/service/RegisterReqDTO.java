package com.wikicoding.jwt_auth.service;

import com.wikicoding.jwt_auth.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterReqDTO {
    private String username;
    private String password;
    private Role role;
}
