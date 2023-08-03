package com.github.bartcowski.infrastructure.authorization.entity;

public record AccessToken(String token, String refreshToken, long expiresInSeconds) {
}
