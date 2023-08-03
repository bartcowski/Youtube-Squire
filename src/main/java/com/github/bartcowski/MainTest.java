package com.github.bartcowski;

import com.github.bartcowski.infrastructure.authorization.entity.AccessToken;
import com.github.bartcowski.infrastructure.authorization.repository.LocalFileOAuthAccessTokenRepository;

public class MainTest {
    public static void main(String[] args) {
        LocalFileOAuthAccessTokenRepository repo = new LocalFileOAuthAccessTokenRepository();
        repo.saveAccessToken(new AccessToken("accessToken123", "refreshToken456", 999));
        System.out.println(repo.findAccessToken().orElse(null));
    }
}
