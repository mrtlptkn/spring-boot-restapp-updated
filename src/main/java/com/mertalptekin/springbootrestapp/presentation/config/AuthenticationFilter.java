package com.mertalptekin.springbootrestapp.presentation.config;

import com.mertalptekin.springbootrestapp.infra.jwt.IJwtService;
import com.mertalptekin.springbootrestapp.infra.repository.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

// OncePerRequestFilter ile her istekde araya girip Authorization Header üzerindeki Bearer değerini okuyacağız.


@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final IJwtService jwtService;
    private final IUserRepository userRepository;

    public AuthenticationFilter(final IJwtService jwtService, IUserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        // token varsa token bizim sistem tarafından üretilen bir token mı?
        String authHeader = request.getHeader("Authorization");

        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            // Burada token doğrulama ve kullanıcı bilgilerini yükleme işlemleri yapılabilir.
            String token = authHeader.substring(7);
            // tokenın içindeki kullanıcı bilgisi.
              String username = jwtService.parseToken(token).getSubject();

            // kullanıcı sistemde kayıtlı mı ?
            UserDetails userDetails =  this.userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found with username: " + username));

                // Token Validate mı ?
                if(jwtService.isTokenValid(token, userDetails)) {
                    System.out.println("Token is valid for user: " + username);

                    // Stateless çalıştığımız için her istekde SecurityContextHolder'a Authentication objesi set etmemiz lazım.

                    // eğer sisteme authenticate olabiliyorsak
                    UsernamePasswordAuthenticationToken authenticationToken =  new UsernamePasswordAuthenticationToken(
                            userDetails.getUsername(),
                            userDetails.getPassword(),
                            userDetails.getAuthorities()
                    );


                    // Security Filterdan geçmek için sisteme authenticationToken token set et ve
                    // oturum açılmış olsun.
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    // isteği kaldığımız yerden devam ettir.
                    filterChain.doFilter(request, response);

                } else {
                    System.out.println("Invalid token for user: " + username);
                    // Eğer token var ama authenticated olamazsak istek yani token valid değilse.
                    filterChain.doFilter(request, response); // 401 döner.
                }

        } else {
            // Zaten headerdan token gelmiyor. Securtiy condif deki permitAll dışında tüm tanımlamalar için isteği kes. 401 döndür.
            // .requestMatchers("/api/v1/demo/**").hasAuthority("ROLE_MANAGER") bu kod takılır.
            // veya controller içerisinde
            // @PreAuthorize("hasRole('ADMIN') and hasAuthority('ROLE_MANAGER')") takılır. Bunları geçemeyeceğinden 401 döner.
            // .requestMatchers("/api/v1/products/**").authenticated()
            System.out.println("No Bearer token found in Authorization header.");
            filterChain.doFilter(request, response); // 401 döner.
        }

    }
}
