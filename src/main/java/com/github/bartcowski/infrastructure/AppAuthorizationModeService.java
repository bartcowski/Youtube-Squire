package com.github.bartcowski.infrastructure;

import com.github.bartcowski.infrastructure.authorization.AuthMethodType;
import com.github.bartcowski.infrastructure.authorization.AuthorizationSupplier;
import com.github.bartcowski.infrastructure.authorization.client.BrowserOpener;
import com.github.bartcowski.infrastructure.authorization.client.OAuthRestClient;
import com.github.bartcowski.infrastructure.authorization.entity.AccessToken;
import com.github.bartcowski.infrastructure.authorization.entity.AuthorizationCode;
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
public class AppAuthorizationModeService {

    private final AppAuthorizationModeProvider authorizationModeProvider;

    private final AuthorizationSupplier authorizationSupplier;

    private final OAuthRestClient authRestClient;

    private final OAuthAccessTokenRepository oAuthAccessTokenRepository;

    void setMode() {
        AuthMethodType authMethodType = authorizationModeProvider.getMethodType();
        if (authMethodType == AuthMethodType.O_AUTH) {
            goThroughOAuthFlow();
        }
        authorizationSupplier.setCurrentAuthMethodType(authMethodType);
    }

    private void goThroughOAuthFlow() {
        CompletableFuture<AuthorizationCode> authCodeCompletableFuture = new CompletableFuture<>();
        OAuthCallbackServer.start(authCodeCompletableFuture);

        BrowserOpener.browse(authRestClient.getUrlToRequestAuthorizationCode());

        AuthorizationCode authCode;
        try {
            authCode = authCodeCompletableFuture.get(120, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }

        System.out.println("OAuth authorization code successfully acquired");

        AccessToken accessToken = authRestClient.getAccessToken(authCode);
        oAuthAccessTokenRepository.saveAccessToken(accessToken);
        System.out.println("OAuth access token successfully acquired and saved");
    }
}
