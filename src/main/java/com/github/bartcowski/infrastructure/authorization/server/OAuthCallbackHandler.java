package com.github.bartcowski.infrastructure.authorization.server;

import com.github.bartcowski.infrastructure.authorization.entity.AuthorizationCode;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

class OAuthCallbackHandler implements HttpHandler {

    private final CompletableFuture<AuthorizationCode> authCodeCompletableFuture;

    OAuthCallbackHandler(CompletableFuture<AuthorizationCode> authCodeCompletableFuture) {
        this.authCodeCompletableFuture = authCodeCompletableFuture;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        System.out.println("Received query from oAuth callback: " + query);

        Map<String, String> queryParams = parseQueryParameters(query);
        String authorizationCode = queryParams.get("code");

        if (authorizationCode == null) {
            throw new RuntimeException("OAUTH WENT WRONG!");
        }
        authCodeCompletableFuture.complete(new AuthorizationCode(authorizationCode));

        String response = "Authorization successful! You can close this window.";
        exchange.sendResponseHeaders(200, response.length());
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();
    }

    private Map<String, String> parseQueryParameters(String query) {
        Map<String, String> queryParams = new HashMap<>();
        if (query == null) {
            return queryParams;
        }

        for (String param : query.split("&")) {
            String[] keyValue = param.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8);
                queryParams.put(key, value);
            }
        }
        return queryParams;
    }
}
