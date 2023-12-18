package com.eozcan.jwt.services;

import com.eozcan.jwt.dtos.SignupRequest;
import com.eozcan.jwt.dtos.UserDTO;

public interface AuthService //Authentication Service
{
    UserDTO createUser(SignupRequest signupRequest); //Create a new user

}
