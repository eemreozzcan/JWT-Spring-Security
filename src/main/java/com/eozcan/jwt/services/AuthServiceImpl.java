package com.eozcan.jwt.services;

import com.eozcan.jwt.dtos.SignupRequest;
import com.eozcan.jwt.dtos.UserDTO;
import com.eozcan.jwt.models.User;
import com.eozcan.jwt.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service //Spring annotation to indicate that the class is a service
public class AuthServiceImpl implements AuthService
{
    //User repository
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDTO createUser(SignupRequest signupRequest) {
        User user = new User();
        //user.setId(signupRequest.getId());
        user.setEmail(signupRequest.getEmail()); //Set user email
        user.setName(signupRequest.getName());   //Set user name
        user.setPhone(signupRequest.getPhone()); //Set user phone
        //Set user password
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        User createdUser = userRepository.save(user); //Save user
        UserDTO userDTO = new UserDTO(); //Create user DTO
        userDTO.setId(createdUser.getId()); //Set user id
        userDTO.setEmail(createdUser.getEmail());  //Set user email
        userDTO.setName(createdUser.getName());  //Set user name
        userDTO.setPhone(createdUser.getPhone()); //Set user phone
        return userDTO; //Return user DTO
    }
}
