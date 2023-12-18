package com.eozcan.jwt.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component //Spring annotation to indicate that the class is a component
public class JwtUtil {
    /*It represents the secret key used when creating a JSON Web Token (JWT). This secret key is used to sign and verify the JWT.
    JWT is a standard used for security controls such as user authentication and authorization, representing a data structure containing a set of information.
    It is important to securely store this secret key because compromising it could jeopardize the secure usage of JWT. For security reasons,
    in a real application, such secret keys are typically stored securely and not shared.*/
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576";

    //Extract username from token
    //"The purpose of this method is to extract the username associated with the provided JWT (JSON Web Token) and return that username.
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);

    }

    //Extract expiration date from token
    //"The purpose of this method is to extract the expiration date associated with the provided JWT (JSON Web Token) and return that expiration date.
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);

    }

    //Extract claim from token
    /*The purpose of this method is to extract a claim from the specified JSON Web Token (JWT). The method utilizes the extractAllClaims
    method to extract all claims from a JWT and then applies a provided function (claimsResolver) to these claims. The function takes a Claims object
    as a parameter and is used to extract a specific claim. As a result, this method returns the value of the extracted claim from the specified JWT.*/
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //Extract all claims from token
    //"The purpose of this method is to extract all claims from the specified JSON Web Token (JWT). The method utilizes the Jwts.parserBuilder() method to
    //create a new JwtParserBuilder instance. The setSigningKey method is then used to set the signing key for the JwtParserBuilder instance. The build method
    //is then used to create a new JwtParser instance. The parseClaimsJws method is then used to parse the specified JWT and return the claims associated with
    //that JWT."
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    //Check if token is expired
    //"The purpose of this method is to check if the specified JSON Web Token (JWT) is expired. The method utilizes the extractExpiration method to extract
    //the expiration date associated with the specified JWT. The method then compares the expiration date with the current date and returns true if the
    //expiration date is before the current date and false otherwise."
    private Boolean isTokenExpired(String token) {
        final Date expiration = extractExpiration(token);
        return expiration.before(new Date());
    }

    //Validate token
    /*The purpose of this method is to validate the specified JSON Web Token (JWT). The method utilizes the extractUsername method to extract the username
    associated with the specified JWT. The method then compares the username with the username associated with the specified UserDetails object. The method
    also utilizes the isTokenExpired method to check if the specified JWT is expired. The method returns true if the username associated with the specified
    JWT is the same as the username associated with the specified UserDetails object and the specified JWT is not expired. The method returns false otherwise.
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }

    //Generate token
    /*The purpose of this method is to generate a JSON Web Token (JWT) for the specified username. The method utilizes the createToken method to create a
    JWT for the specified username. The method then returns the generated JWT.
     */
    public String generateToken(String userName) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userName);
    }

    //Create token
    /*The purpose of this method is to create a JSON Web Token (JWT) for the specified username. The method utilizes the Jwts.builder() method to create a
    JwtBuilder instance. The setClaims method is then used to set the claims for the JwtBuilder instance. The setSubject method is then used to set the
    subject for the JwtBuilder instance. The setIssuedAt method is then used to set the issued at date for the JwtBuilder instance. The setExpiration method
    is then used to set the expiration date for the JwtBuilder instance. The signWith method is then used to set the signing key and signature algorithm for
    the JwtBuilder instance. The compact method is then used to compact the JwtBuilder instance and return the generated JWT.
     */
    public String createToken(Map<String, Object> claims, String userName) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    //Get signing key
    /*The purpose of this method is to get the signing key used when creating a JSON Web Token (JWT). The method utilizes the Decoders.BASE64.decode method
    to decode the secret key. The Keys.hmacShaKeyFor method is then used to create a new Key instance from the decoded secret key. The method then returns
    the created Key instance.*/
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Long getExpirationTimeInMillis() {
        return 86400000L;    }
}
