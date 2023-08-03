package com.github.bartcowski.infrastructure.authorization.repository;

import com.github.bartcowski.infrastructure.authorization.entity.AccessToken;

import java.util.Optional;

public interface OAuthAccessTokenRepository {

    void saveAccessToken(AccessToken accessToken);

    Optional<AccessToken> findAccessToken();

}
