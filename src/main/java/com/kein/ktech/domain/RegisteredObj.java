package com.kein.ktech.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredObj {
    private String name;
    private String email;
    private String password;
    private String confirmPassword;
}
