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


        String authHeader = request.getHeader("Authorization");

        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            // Burada token doğrulama ve kullanıcı bilgilerini yükleme işlemleri yapılabilir.
            String token = authHeader.substring(7);

            // Token gönderirse budurumda doğrulama işlemleri yapılabilir. Bu token geçerli mi, süresi dolmuş mu gibi kontroller yapılabilir.
            System.out.println("Token: " + token);


            // Login olurken oluşturuğum kullanıcı hesabı üzerinden yenide her istek de login miyim kontrolünü token üzerinden yapmam lazım
            // Bunu yaparkende UserDetailsService'den kullanıcı bilgileri ile tokendan gelen kullanıcı bilgilerini kıyalayıp, token bilgisinin expire olmağını kontrol etmem lazım.

            // Eğer kullanıcı ile tokendaki kullanıcı bilgisi eşleşiyorsa, bu token kullanıcya aittir. Çünkü kullanıcının parolasını token içinde güvenllik endişesi sebebi ile saklayamayız.

            // Token expr değilse ve kullanıcı oturumu veritabnındaki kullanıcı ile eşleşiyorsa, bu userDetails bilgisi ile yeniden uygulamada oturum açmaya çalış.

              String username = jwtService.parseToken(token).getSubject();
//            List<GrantedAuthority> authority = (List<GrantedAuthority>) jwtService.parseToken(token).get("roles");
//


            UserDetails userDetails =  this.userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found with username: " + username));



                // Tokenın validate edilmesi lazım.
                if(jwtService.isTokenValid(token, userDetails)) {
                    System.out.println("Token is valid for user: " + username);

                    // Stateless çalıştığımız için her istekde SecurityContextHolder'a Authentication objesi set etmemiz lazım.

                    UsernamePasswordAuthenticationToken authenticationToken =  new UsernamePasswordAuthenticationToken(
                            userDetails.getUsername(),
                            userDetails.getPassword(),
                            userDetails.getAuthorities()
                    );

                    // Uygulama beni Authenticated kabul etsin.
                    // Security Config .anyRequest().authenticated()); dediğimizden uygulamının token gönderen kullanıcıyı hesap açmış gibi görmesi lazım.
                    // Normal Web uygulamarında cookie ile bunu yönetirdik. Her istekde cookiedan oturum kontrolü yapardık. Ama RestServislerde bu kontrol tokendan yönetilmelidir. Stateless durumsuz bir yapı olması sebebi ile cookie üzerinden bu işlemleri session bazlı yönetemediğimizden dolayı Client Header üzerinden  Authorization: Bearer Token ile token gönderir.
                    // Bizde burada token göre oturum yönetimini anlık yapmalıyız.
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);

                } else {
                    System.out.println("Invalid token for user: " + username);
                    // Süreci artık SecurityFilterChain'e devret, SecurityConfig'de tanımlanan kurallar işleyecek.
                    filterChain.doFilter(request, response); // 401 döner.
                }


        } else {
            // kullanıcı eğer authenticated olan bir endpointe token göndermezse bu durumda SecurityConfig'de tanımladığımız gibi 401 hatası dönecektir.

            // bunlar dışında 401 alacağız.
            //  .requestMatchers("/api/demo/**").permitAll()
            //  .requestMatchers(("/api/auth/**")).permitAll()
            //  .requestMatchers("/h2-console/**").permitAll()
            // Süreci SecurityFilterChain'e devret, SecurityConfig'de tanımlanan kurallar işleyecek.
            System.out.println("No Bearer token found in Authorization header.");
            filterChain.doFilter(request, response); // 401 döner.

        }

    }
}
