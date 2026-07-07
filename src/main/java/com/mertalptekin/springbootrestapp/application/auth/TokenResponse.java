package com.mertalptekin.springbootrestapp.application.auth;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Token Response Model", hidden = true)
public record TokenResponse(String accessToken) {
}
