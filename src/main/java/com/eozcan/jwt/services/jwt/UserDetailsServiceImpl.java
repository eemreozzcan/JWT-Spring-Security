package com.eozcan.jwt.services.jwt;

import com.eozcan.jwt.models.User;
import com.eozcan.jwt.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service //Spring annotation to indicate that the class is a service
public class UserDetailsServiceImpl implements UserDetailsService
{
    @Autowired //Spring annotation to inject a bean
    private UserRepository userRepository; //User repository

    @Override //Override the method
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException //Load user by username
    {
        //Find user by email
        User user = userRepository.findFirstByEmail(email);

        //If user is null
        if(user == null)
        {
            //Throw UsernameNotFoundException
            throw new UsernameNotFoundException("User Not Found: " + email);
        }

        //Return user details
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                                                                      user.getPassword(),

                new ArrayList<>());
    }
}
