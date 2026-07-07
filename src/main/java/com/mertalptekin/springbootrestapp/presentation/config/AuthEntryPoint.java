package com.mertalptekin.springbootrestapp.presentation.config;

import com.mertalptekin.springbootrestapp.infra.jwt.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

// Kimlik doğrulaması başarısız olduğunda çağrılan bileşen

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {

    private final JwtService jwtService;

    public AuthEntryPoint(JwtService jwtService) {
        this.jwtService = jwtService;
    }


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.setContentType("application/json");
        String authHeader = request.getHeader("Authorization");

        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                jwtService.parseToken(token);

                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write(
                        "{ \"error\": \"Forbidden\", " +
                                "\"message\": \"Bu kaynağa erişim yetkiniz yok\" }");

            } catch (Exception e) {
                // Geçersiz token ise 401 Unauthorized döner
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(
                        "{ \"error\": \"Unauthorized\", " +
                                "\"message\": \"Geçersiz token\" }"
                );
            }
        } else {
            // Kullanıcı kimliği doğrulanmamış ise 401
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(
                    "{ \"error\": \"Unauthorized\", " +
                            "\"message\": \"Kimlik doğrulama gerekli\" }"
            );

        }


    }
}
