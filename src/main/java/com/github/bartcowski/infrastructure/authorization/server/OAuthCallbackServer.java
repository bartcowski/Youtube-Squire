package com.github.bartcowski.infrastructure.authorization.server;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class OAuthCallbackServer {

    static final String HOST = "http://localhost";

    static final int PORT = 8080;

    static final String CALLBACK_CONTEXT = "/oauth/callback";

    private static final HttpServer server;

    private static boolean isActive = false;

    static {
        try {
            server = HttpServer.create(new InetSocketAddress(PORT), 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        server.createContext(CALLBACK_CONTEXT, new OAuthCallbackHandler());
    }

    public static void start() {
        if (isActive) {
            return;
        }
        server.start();
        isActive = true;
        System.out.println("OAuth callback HTTP server listening on port " + PORT);
    }

    public static void stop() {
        server.stop(10);
        isActive = false;
        System.out.println("OAuth callback HTTP server is off");
    }

    public static String getCallbackServerUrl() {
        return HOST + ":" + PORT + CALLBACK_CONTEXT;
    }

}
