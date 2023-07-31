package com.github.bartcowski.infrastructure.authorization.client;

import com.github.bartcowski.infrastructure.authorization.server.OAuthCallbackServer;
import com.github.bartcowski.infrastructure.rest.AbstractYoutubeRestClient;
import com.github.bartcowski.infrastructure.rest.RestRequestBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.github.bartcowski.util.RestUtils.addParamsToUrl;

@Service
public class YoutubeAuthRestClient extends AbstractYoutubeRestClient {

    private static final String GOOGLE_OAUTH_URL = "https://accounts.google.com/o/oauth2/v2/auth";

    private static final String GOOGLE_ACCESS_TOKEN_URL = "https://oauth2.googleapis.com/token";

    private final String clientId;

    private final String clientSecret;

    public YoutubeAuthRestClient(@Value("${oauth.clientId}")String clientId,
                                 @Value("${oauth.clientSecret}")String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String getUrlToRequestAuthorizationCode() {
        return addParamsToUrl(GOOGLE_OAUTH_URL, buildParamsForAuthCodeRequest());
    }

    public String getAccessToken(String authCode) {
        RestRequestBuilder requestBuilder = new RestRequestBuilder(GOOGLE_ACCESS_TOKEN_URL)
                .GET()
                .params(buildParamsForAccessTokenRequest(authCode));

        return sendRequest(requestBuilder, AccessTokenResponseDTO.class).access_token;
    }

    private Map<String, String> buildParamsForAuthCodeRequest() {
        String responseType = "code";
        String scope = "https://www.googleapis.com/auth/youtube.readonly";

        return Map.of(
                "client_id", clientId,
                "redirect_uri", OAuthCallbackServer.getCallbackServerUrl(),
                "response_type", responseType,
                "scope", scope
        );
    }

    private Map<String, String> buildParamsForAccessTokenRequest(String authCode) {
        return Map.of(
                "client_id", clientId,
                "client_secret", clientSecret,
                "code", authCode,
                "grant_type", "authorization_code",
                "redirect_uri", OAuthCallbackServer.getCallbackServerUrl()
        );
    }

    private static class AccessTokenResponseDTO {
        String access_token;
        long expires_in;
        String refresh_token;
    }
}
