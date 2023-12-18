package com.eozcan.jwt.dtos;

import lombok.Data;

@Data //Lombok annotation to generate getters and setters
public class AuthencationRequest
{
    private String email; //User email
    private String password; //User password
}
