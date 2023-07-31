package com.github.bartcowski.infrastructure.authorization;

import com.github.bartcowski.infrastructure.authorization.repository.OAuthAccessTokenRepository;
import com.github.bartcowski.infrastructure.rest.RestRequestBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OAuthAuthorizer implements RequestAuthorizer {

    private final OAuthAccessTokenRepository accessTokenRepository;

    //TODO: might for as a cache to limit file access? What about refreshing this value?
    //private String accessToken;

    @Override
    public void authorize(RestRequestBuilder requestBuilder) {
        String accessToken = accessTokenRepository.findAccessToken().orElseThrow(RuntimeException::new);
        String authHeaderName = "Authorization";
        String authHeaderValue = "Bearer " + accessToken;
        requestBuilder.header(authHeaderName, authHeaderValue);
    }

    @Override
    public boolean isApplicableFor(AuthMethodType authMethodType) {
        return AuthMethodType.O_AUTH == authMethodType;
    }
}
