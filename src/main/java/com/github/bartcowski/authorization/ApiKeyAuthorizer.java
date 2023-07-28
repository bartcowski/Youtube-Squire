package com.github.bartcowski.authorization;

import com.github.bartcowski.rest.RestRequestBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
class ApiKeyAuthorizer implements RequestAuthorizer {

    private final String apiKey;

    ApiKeyAuthorizer(@Value("${apiKey}") String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public void authorize(RestRequestBuilder requestBuilder) {
        Map<String, String> authParams = Map.of("key", apiKey);
        requestBuilder.params(authParams);
    }

    @Override
    public boolean isApplicableFor(AuthMethodType authMethodType) {
        return AuthMethodType.API_KEY == authMethodType;
    }
}
