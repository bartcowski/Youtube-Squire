package com.github.bartcowski.authorization;

import com.github.bartcowski.rest.RestRequestBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class OAuthAuthorizer implements RequestAuthorizer {

    private final String accessToken;

    OAuthAuthorizer(@Value("${oauth.accesstoken}") String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public void authorize(RestRequestBuilder requestBuilder) {
        String authHeaderName = "Authorization";
        String authHeaderValue = "Bearer " + accessToken;
        requestBuilder.header(authHeaderName, authHeaderValue);
    }

    @Override
    public boolean isApplicableFor(AuthMethodType authMethodType) {
        return AuthMethodType.O_AUTH == authMethodType;
    }
}
