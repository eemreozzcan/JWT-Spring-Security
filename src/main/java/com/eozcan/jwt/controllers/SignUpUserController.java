package com.eozcan.jwt.controllers;

import com.eozcan.jwt.dtos.SignupRequest;
import com.eozcan.jwt.dtos.UserDTO;
import com.eozcan.jwt.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController/*@RestController annotation marks a Spring class as a RESTful web service.
Classes annotated with @RestController automatically convert each method result into HTTP responses.*/
public class SignUpUserController {

    /*"It injects an object named authService using the @Autowired annotation. This is used to automatically wire dependencies with a component managed by Spring."*/
    @Autowired
    private AuthService authService;

    /*It invokes the createUser method when an HTTP POST request is received and matches the "/register" URL. This method takes a user registration request of
     type SignupRequest and creates a new user using the createUser method provided by authService.*/
    //http://localhost:8080/register
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody SignupRequest signupRequest) {

        /*It creates a user by using the createUser method provided by authService and assigns the created user to the variable createdUser, which is of type UserDTO.*/
        UserDTO createdUser = authService.createUser(signupRequest);

        /*If the createdUser is null, it returns an HTTP response with a status code of 400 (BAD_REQUEST) and a message indicating that the user is not created.*/
        if (createdUser == null) {
            return new ResponseEntity<>("User is not created, try again later", HttpStatus.BAD_REQUEST);
        }
        /*If the createdUser is not null, it returns an HTTP response with a status code of 201 (CREATED) and the createdUser object.*/
         return new ResponseEntity<>(createdUser, HttpStatus.CREATED);

    }
}
