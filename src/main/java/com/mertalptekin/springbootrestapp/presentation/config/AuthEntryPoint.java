package com.mertalptekin.springbootrestapp.presentation.config;

import com.mertalptekin.springbootrestapp.infra.jwt.JwtService;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.SignatureException;

// Kimlik doğrulaması başarısız olduğunda çağrılan bileşen

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {



    public AuthEntryPoint(JwtService jwtService) {

    }


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {



                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write(authException.getMessage());
    }
}
