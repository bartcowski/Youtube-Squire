package com.github.bartcowski.authorization;

import com.github.bartcowski.rest.RestRequestBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorizationSupplier {

    private final List<RequestAuthorizer> requestAuthorizers;

    private AuthMethodType currentAuthMethodType = AuthMethodType.API_KEY;

    public void setCurrentAuthMethodType(AuthMethodType authMethodType) {
        this.currentAuthMethodType = authMethodType;
    }

    public void authorize(RestRequestBuilder requestBuilder) {
        requestAuthorizers.stream()
                .filter(authMethod -> authMethod.isApplicableFor(currentAuthMethodType))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No authorizer found for method:" + currentAuthMethodType))
                .authorize(requestBuilder);
    }
}
