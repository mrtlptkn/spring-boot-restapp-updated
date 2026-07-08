package com.mertalptekin.springbootrestapp.presentation.config;


import com.mertalptekin.springbootrestapp.domain.service.CustomUserDetailService;
import com.mertalptekin.springbootrestapp.infra.repository.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Configuration sınıfında manuel bean tanımlamaları yapılabilir.
@Configuration
public class AppConfig {


    private final CustomUserDetailService customUserDetailService;

    public AppConfig(IUserRepository userRepository, CustomUserDetailService customUserDetailService) {
        this.customUserDetailService = customUserDetailService;
    }

    @Bean(name = "getAppName1")
    public String getAppName() {
        return "Spring Boot Rest App 01";
    }


    // Manuel olarak Entityleri Dto Mapleyecek yapının instance spring context veriyoruz.
    // Pom xml
    //  <dependency>
    //            <groupId>org.modelmapper</groupId>
    //            <artifactId>modelmapper</artifactId>
    //            <version>3.1.1</version>
    //        </dependency>
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


    // sistemde oturum açmak isteyen kullanıcı bilgilerini veritabanından yüklediğimiz servis
    // Bean
    @Bean
    public UserDetailsService userDetailsService() {
        return customUserDetailService;
    }

    // Password encoder bean tanımı, şifreleri güvenli bir şekilde saklamak için kullanılır.
    // user login olurken kullanıcı parola girecek. bu girilen paralo ile veritabanında şifrelenmiş
    // olan paralonın hash eşleşmesi lazım. Bunu spring Security otomatik olarak yönetir.
    // bu Bean ise bu login sürecinde paralonun hashlenmesi ve hashlenmiş parolaların kıyaslanması için
    // var.
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Burada kullanıcı bilgisi veritabanından çekilrirken hangi şifreleme bean ve
    // hangi servis kullanılacağını belirtiyoruz.
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // login işlemi sırasında authentication sürecini yöneten servisimi ise bu.
    // AuthenticationManager bean tanımı, kimlik doğrulama işlemlerini yönetir.
    // APIda UsernamePasswordAuthenticationToken bazlı kimlik doğrulama işlemleri için kullanacağız.
    // AuthenticationManager, Spring Securityde birden fazla kimlik doğrulama yönetini yöneten sınıftır.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
            return authenticationConfiguration.getAuthenticationManager();
    }



}
