package com.github.bartcowski.infrastructure;

import com.github.bartcowski.infrastructure.authorization.AuthMethodType;
import com.github.bartcowski.infrastructure.authorization.AuthorizationSupplier;
import com.github.bartcowski.infrastructure.authorization.client.BrowserOpener;
import com.github.bartcowski.infrastructure.authorization.client.YoutubeAuthRestClient;
import com.github.bartcowski.infrastructure.authorization.repository.OAuthAccessTokenRepository;
import com.github.bartcowski.infrastructure.authorization.server.OAuthCallbackServer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@AllArgsConstructor
public class AuthorizationModeService {

    private final AppAuthorizationModeProvider authorizationModeProvider;

    private final AuthorizationSupplier authorizationSupplier;

    private final YoutubeAuthRestClient authRestClient;

    private final OAuthAccessTokenRepository oAuthAccessTokenRepository;

    void setMode() {
        AuthMethodType authMethodType = authorizationModeProvider.getMethodType();
        if (authMethodType == AuthMethodType.O_AUTH) { //TODO: should this if and whole flow be here?
            goThroughOAuthFlow();
        }
        authorizationSupplier.setCurrentAuthMethodType(authMethodType);
    }

    private void goThroughOAuthFlow() {
        CompletableFuture<String> authCodeCompletableFuture = new CompletableFuture<>();
        OAuthCallbackServer.start(authCodeCompletableFuture);

        BrowserOpener.browse(authRestClient.getUrlToRequestAuthorizationCode());

        String authCode;
        try {
            authCode = authCodeCompletableFuture.get(60, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }

        System.out.println("OAuth authorization code successfully acquired");

        String accessToken = authRestClient.getAccessToken(authCode);
        if (accessToken == null || accessToken.isEmpty()) {
            throw new RuntimeException("Acquired access token is invalid!");
        }

        oAuthAccessTokenRepository.saveAccessToken(accessToken);
        System.out.println("OAuth access token successfully acquired and saved");
    }
}
