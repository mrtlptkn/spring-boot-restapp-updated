package com.mertalptekin.springbootrestapp.infra.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.config.annotation.web.oauth2.resourceserver.JwtDsl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtService implements IJwtService {

    // Azure Key Vault, AWS Secret Manager gibi servislerde saklanmalı.
    private final String secretKey = "1c8201d66f16acfc20c008a4f3b4904b928764e6f48a3734a999181346e4a93x";


    @Override
    public String generateToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();
        List<GrantedAuthority> authorities = userDetails.getAuthorities().stream().map(authority -> new SimpleGrantedAuthority(authority.toString())).collect(Collectors.toList());
        claims.put("roles", authorities);

        // tokenda role değerleri eklenmiş oldu.

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .addClaims(claims) // additional claims setledik, set dersek diğer claimleri ezer.addtional claims ekleme işlemi için addClaims kullanılır.
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) // 30 dakika geçerli
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256,
                        io.jsonwebtoken.security.Keys.hmacShaKeyFor(secretKey.getBytes())
                ).setIssuedAt(new Date(System.currentTimeMillis()))
                .compact();
    }

    // Token parse etme ve içerisindeki claimleri alma
    public Claims parseToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(io.jsonwebtoken.security.Keys.hmacShaKeyFor(secretKey.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }



    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        Claims claims = parseToken(token);
        // Token süresi dolmuş mu ve kullanıcı adı eşleşiyor mu kontrolü
        if(new Date().before(claims.getExpiration()) && claims.getSubject().equals(userDetails.getUsername())){
            return true;
        }

        return false;
    }
}
