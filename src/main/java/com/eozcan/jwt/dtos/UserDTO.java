package com.eozcan.jwt.dtos;

import lombok.Data;

@Data //Lombok annotation to generate getters and setters
public class UserDTO //User Data Transfer Object
{
    private Long id; //User id
    private String name; //User name
    private String email; //User email
    private String phone;
}
