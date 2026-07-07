package com.mertalptekin.springbootrestapp.infra.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {

    String generateToken(UserDetails userDetails);

    Claims parseToken(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
}
