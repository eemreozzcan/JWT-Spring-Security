package com.eozcan.jwt.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data //Lombok annotation to generate getters and setters
@AllArgsConstructor //Lombok annotation to generate a constructor with all arguments
public class AuthenticationResponse
{
    private String jwt; //JSON Web Token
    private Long expirationTime; //Token expiration time
}
