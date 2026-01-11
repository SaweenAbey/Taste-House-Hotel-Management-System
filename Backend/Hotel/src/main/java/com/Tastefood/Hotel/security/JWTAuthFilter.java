package com.Tastefood.Hotel.security;


import com.Tastefood.Hotel.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JWTAuthFilter {

    @Autowired
    private JWTUtils jwtUtils;




}
