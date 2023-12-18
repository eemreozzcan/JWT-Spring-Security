package com.eozcan.jwt.controllers;

import com.eozcan.jwt.dtos.AuthencationRequest;
import com.eozcan.jwt.dtos.AuthenticationResponse;
import com.eozcan.jwt.services.jwt.UserDetailsServiceImpl;
import com.eozcan.jwt.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController /*@RestController annotation marks a Spring class as a RESTful web service.
Classes annotated with @RestController automatically convert each method result into HTTP responses.*/
public class AuthenticationController
{
    /*This code snippet injects a dependency of type AuthenticationManager using the @Autowired annotation. Consequently,
    it provides access to the AuthenticationManager within the class through a variable named authenticationManager.*/
    @Autowired
    private AuthenticationManager authenticationManager;

    /*This code snippet injects a dependency of type UserDetailsServiceImpl using the @Autowired annotation.
    Thus, it provides access to the UserDetailsServiceImpl within the class through a variable named userDetailsService.*/
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /*This code snippet injects a dependency of type JwtUtil using the @Autowired annotation. Thus, it provides access to the JwtUtil within the class
    through a variable named jwtUtil. This is typically used in operations related to JSON Web Token (JWT), such as authentication and authorization.*/
    @Autowired
    private JwtUtil jwtUtil;

    //Create authentication token
    //http://localhost:8080/authentication

    @PostMapping("/authentication") /*This annotation specifies that this method will be called when an HTTP POST request is received, and it maps to the "/authentication" URL.*/

    /*This method is invoked when a user sends an authentication request. It takes the user credentials in the form of an AuthencationRequest object
    (authencationRequest) and an HttpServletResponse object to manage HTTP responses. The method performs the authentication process,
    and upon successful authentication, it returns an AuthenticationResponse object containing a JWT (JSON Web Token). If authentication fails,
    it throws appropriate exceptions and handles the necessary processes for managing these exceptions.*/
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken  (@RequestBody AuthencationRequest authencationRequest,
                                                                              HttpServletResponse response)
    throws BadCredentialsException,
            DisabledException,
            UsernameNotFoundException,
            IOException
    {

        /*This section is a block that performs the user authentication process. authenticationManager is a component provided by Spring Security,
         and it authenticates the user by creating a UsernamePasswordAuthenticationToken using the user's credentials (authencationRequest.getEmail()
         and authencationRequest.getPassword()) and calling the authenticate method. If the authentication is unsuccessful, the execution proceeds to the
         catch blocks within this block.*/
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authencationRequest.getEmail(),
                            authencationRequest.getPassword()));
        }
        /*"If the authentication process fails (e.g., due to incorrect username or password), it creates an exception of type BadCredentialsException
        and throws this exception with the message 'Incorrect username or password'."*/
        catch (BadCredentialsException e) {
          throw  new BadCredentialsException("Incorrect username or password",e);
        }
        /*If the user is not active (DisabledException condition), it sends an error response to the HTTP with the message 'User is not created.
        Register User First' and adds the '404 Not Found' HTTP status code, returning null.*/
        catch (DisabledException disabledException) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User is not created.Register User First");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        /*It loads details related to the user's identity using the loadUserByUsername method provided by userDetailsService.*/

        final var userDetails = userDetailsService.loadUserByUsername(authencationRequest.getEmail());

        /*It creates a JWT (JSON Web Token) by using the generateToken method of the jwtUtil object. The generated token is typically derived from the user
        details (username) provided at this point. The resulting JWT is often utilized in the authentication process or as part of the response to the user.*/
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        final Long expirationTimeMillis = jwtUtil.getExpirationTimeInMillis();


        /*It returns an AuthenticationResponse object containing the generated JWT.*/
        return ResponseEntity.ok(new AuthenticationResponse(jwt, expirationTimeMillis));
    }
    //Handle bad credentials exception
    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(Exception e) {
        ErrorResponse errorResponse = new ErrorResponse("Invalid username or password", HttpStatus.UNAUTHORIZED.value());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }
    //Handle disabled exception
    @Data
    @AllArgsConstructor
    private static class ErrorResponse {
        private String message;
        private int status;
    }
}
