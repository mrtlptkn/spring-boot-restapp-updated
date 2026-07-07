package com.mertalptekin.springbootrestapp.application.auth;

import io.swagger.v3.oas.annotations.media.Schema;

public record TokenRequest(

        @Schema(description = "Kullanıcı adı", example = "testuser", defaultValue = "sa")
        String username,
        @Schema(description = "Kullanıcı Parola", example = "P@ssword1", defaultValue = "pwd")
        String password) {
}
