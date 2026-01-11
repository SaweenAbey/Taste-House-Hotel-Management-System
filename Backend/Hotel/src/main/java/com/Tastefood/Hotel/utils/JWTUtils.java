package com.Tastefood.Hotel.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Service
public class JWTUtils {

    private static final long Expire_Time = 1000*60*24*7; //for seven days

    private final SecretKey Key;
    public JWTUtils(){
        String secretString = "YjN0Q1FhZVhXcE5MZ2ZpR3VnT0h4R1Z4cGJrU2F3eXk1N1FQbU9yV0ZqY1FJc1E5RQ==";
        byte[] keyBytes = Base64.getDecoder().decode(secretString.getBytes(StandardCharsets.UTF_8));
        this.Key = new SecretKeySpec(keyBytes,"HmacSHA256");

    }

    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ Expire_Time))
                .signWith(Key)
                .compact();

    }
    public String extractUsername(String token){
        return  extractClaims(token, Claims::getSubject);

    }
    private <T> T extractClaims(String token, Function<Claims,T> claimsTFunction){

        return claimsTFunction.apply(Jwts.parser().verifyWith(Key).build().parseSignedClaims(token).getPayload());

    }
    public boolean isValidToken(String token,UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));

    }

    private boolean isTokenExpired(String token){
        return extractClaims(token,Claims::getExpiration).before(new Date());
    }



}
