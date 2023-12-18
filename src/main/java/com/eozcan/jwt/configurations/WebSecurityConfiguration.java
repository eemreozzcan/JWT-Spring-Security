package com.eozcan.jwt.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static ch.qos.logback.classic.spi.ThrowableProxyVO.build;

@Configuration /*"The @Configuration annotation is used in the Spring framework to designate configuration classes,
which typically include bean definitions and application configurations."*/
@EnableWebSecurity /*"@EnableWebSecurity annotation is used to provide Spring Security configuration, and the class containing this annotation enables web security features."*/
@EnableMethodSecurity /*"@EnableMethodSecurity annotation is used to enable method-level security configuration in Spring Security."*/

public class WebSecurityConfiguration {

    /*"This code provides a PasswordEncoder bean of type BCryptPasswordEncoder for use in a Spring Security application."*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }

    /*This code provides a SecurityFilterChain bean for Spring Security and defines the configuration for HTTP security.
    It disables CSRF protection, sets session management to STATELESS, and specifies authorization controls for the specified*/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
            throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable) /*"Disables CSRF (Cross-Site Request Forgery) protection in Spring Security configuration,"*/
                /*Specifies the session management in Spring Security configuration and sets the sessions to be STATELESS, indicating that the application will not maintain
                user sessions, and each HTTP request is independent with no preserved session state.*/
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                /*"Configures authorization for specified URLs and grants permission to all users for the URLs "/register", "/api/**", and "/authentication"."*/
                .authorizeHttpRequests(c -> c.requestMatchers("/register", "/api/**","/authentication").permitAll()
                /*"Specifies that authentication is required for all HTTP requests, meaning users need to be logged in."*/
                .anyRequest().authenticated())
                .build();
    }

    /*This code provides an AuthenticationManager bean for Spring Security configuration, representing the application's authentication management.*/
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }


}
