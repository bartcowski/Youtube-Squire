package com.github.bartcowski;

import com.github.bartcowski.infrastructure.authorization.repository.LocalFileOAuthAccessTokenRepository;

public class MainTest {
    public static void main(String[] args) {
        LocalFileOAuthAccessTokenRepository repo = new LocalFileOAuthAccessTokenRepository();
        //repo.saveAccessToken("qwertyuiop_1234567890");
        System.out.println(repo.findAccessToken().orElse("NOT FOUND"));
    }
}
