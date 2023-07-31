package com.github.bartcowski.infrastructure.authorization.repository;

import java.util.Optional;

public interface OAuthAccessTokenRepository {

    void saveAccessToken(String accessToken);

    Optional<String> findAccessToken();

}
