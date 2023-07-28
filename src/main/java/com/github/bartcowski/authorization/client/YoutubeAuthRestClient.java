package com.github.bartcowski.authorization.client;

import com.github.bartcowski.authorization.server.OAuthCallbackServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.github.bartcowski.util.RestUtils.addParamsToUrl;

@Service
public class YoutubeAuthRestClient {

    private static final String GOOGLE_OAUTH_URL = "https://accounts.google.com/o/oauth2/v2/auth";

    private final String clientId;

    public YoutubeAuthRestClient(@Value("${oauth.clientId}")String clientId) {
        this.clientId = clientId;
    }

    public String getUrlToRequestAuthorizationCode() {
        return addParamsToUrl(GOOGLE_OAUTH_URL, getParams());
    }

    private Map<String, String> getParams() {
        String responseType = "code";
        String scope = "https://www.googleapis.com/auth/youtube.readonly";

        return Map.of(
                "client_id", clientId,
                "redirect_uri", OAuthCallbackServer.getCallbackServerUrl(),
                "response_type", responseType,
                "scope", scope
        );
    }

}
