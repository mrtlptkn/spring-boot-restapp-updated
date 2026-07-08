package com.mertalptekin.springbootrestapp.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

// UserDetails -> Spring Security'nin kullanıcı bilgilerini temsil eden arayüzüdür.

// Bir kullanıcının veritabanı kullanıcısı olup spring security tarafından tanınması için
// UserDetails arayüzünü implemente etmesi gerekir.
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // şifre alanı, hashlenmiş şifre saklanmalı

    // Eager loading ile veri tabanından user çekilirken role tablosundanda role bilgileri
    // ilişki userlar ile birlikte tek bir sorguda çekilir.
    // select * from users left join roles
    @ManyToMany(fetch = FetchType.EAGER)
    // ara bşr tablo varsa Joişn Table açılır
    // user Role entitysi program tarafında yok ama sql tarafında böyle bir tabloya ihtiyaç olduğundan
    // sql tarafında açılır.
    @JoinTable(
            name = "user_roles", // ara tablo adı
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;


    // UserDetails interface methods ile kullanıcıya rol tanımı verdik.
    // Role tablasu olurşturmak yerine buradan verdik.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // role yetkilerini spring security anlasın diye dolduruyoruz
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
