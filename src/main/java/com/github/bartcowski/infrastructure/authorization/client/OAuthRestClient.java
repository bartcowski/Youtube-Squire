package com.github.bartcowski.infrastructure.authorization.client;

import com.github.bartcowski.infrastructure.authorization.entity.AccessToken;
import com.github.bartcowski.infrastructure.authorization.entity.AuthorizationCode;

public interface OAuthRestClient {

    String getUrlToRequestAuthorizationCode();

    AccessToken getAccessToken(AuthorizationCode authCode);

}
