package com.github.bartcowski.infrastructure.authorization;

import com.github.bartcowski.infrastructure.authorization.entity.AccessToken;
import com.github.bartcowski.infrastructure.authorization.repository.OAuthAccessTokenRepository;
import com.github.bartcowski.infrastructure.rest.RestRequestBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OAuthAuthorizer implements RequestAuthorizer {

    private final OAuthAccessTokenRepository accessTokenRepository;

    //TODO: might for as a cache to limit file access? What about refreshing?
    //private AccessToken accessToken;

    @Override
    public void authorize(RestRequestBuilder requestBuilder) {
        AccessToken accessToken = accessTokenRepository.findAccessToken().orElseThrow(RuntimeException::new);
        String authHeaderName = "Authorization";
        String authHeaderValue = "Bearer " + accessToken.token();
        requestBuilder.header(authHeaderName, authHeaderValue);
    }

    @Override
    public boolean isApplicableFor(AuthMethodType authMethodType) {
        return AuthMethodType.O_AUTH == authMethodType;
    }
}
