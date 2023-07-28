package com.github.bartcowski.servertest;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

class MainServerTest {

    public static void main(String[] args) {
        int port = 8888;
        HttpServer server;
        try {
            server = HttpServer.create(new InetSocketAddress(port), 0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        server.createContext("/api/test", new ServerTestHandler());
        server.start();
        System.out.println("HTTP Server listening on port " + port);
    }
}
