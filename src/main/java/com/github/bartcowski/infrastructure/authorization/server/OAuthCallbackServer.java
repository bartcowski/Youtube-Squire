package com.github.bartcowski.infrastructure.authorization.server;

import com.github.bartcowski.infrastructure.authorization.entity.AuthorizationCode;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

public class OAuthCallbackServer {

    private static final String HOST = "http://localhost";

    private static final int PORT = 8080;

    private static final String CALLBACK_CONTEXT = "/oauth/callback";

    private static final HttpServer server;

    private static boolean isActive = false;

    static {
        try {
            server = HttpServer.create(new InetSocketAddress(PORT), 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void start(CompletableFuture<AuthorizationCode> authCodeCompletableFuture) {
        if (isActive) {
            return;
        }
        server.createContext(CALLBACK_CONTEXT, new OAuthCallbackHandler(authCodeCompletableFuture));
        server.start();
        isActive = true;
        System.out.println("OAuth callback HTTP server listening on port " + PORT);
    }

    public static void stop() {
        server.stop(3);
        isActive = false;
        System.out.println("OAuth callback HTTP server is off");
    }

    public static String getCallbackServerUrl() {
        return HOST + ":" + PORT + CALLBACK_CONTEXT;
    }

}
