package com.github.bartcowski.servertest;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

class ServerTestHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("Received request." +
                " path: " + exchange.getRequestURI().getPath() +
                " query: " + exchange.getRequestURI().getQuery() +
                " rawQuery: " + exchange.getRequestURI().getRawQuery());

        String response = "All went fine!";
        exchange.sendResponseHeaders(200, response.length());
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();
    }
}