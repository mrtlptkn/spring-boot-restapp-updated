package com.mertalptekin.springbootrestapp.application.auth;

// Kullanıcı oluşturma işlemi için gerekli Dto
public record RegisterRequest(String username,String password) {
}
