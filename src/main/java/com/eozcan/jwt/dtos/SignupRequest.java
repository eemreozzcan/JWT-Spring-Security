package com.eozcan.jwt.dtos;

import lombok.Data;

@Data //Lombok annotation to generate getters and setters
public class SignupRequest
{
    private Long id; //User id
    private String name; //User name
    private String email; //User email
    private String password; //User password
    private String phone; //User phone
}
