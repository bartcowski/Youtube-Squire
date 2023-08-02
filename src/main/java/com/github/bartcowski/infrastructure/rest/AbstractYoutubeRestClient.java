package com.github.bartcowski.infrastructure.rest;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public abstract class AbstractYoutubeRestClient {

    protected static final String BASE_URL = "https://www.googleapis.com/youtube/v3";

    private final Gson gson = new Gson();

    protected <T> T sendRequest(RestRequestBuilder requestBuilder, Class<T> returnClass) {
        final HttpRequest request = requestBuilder.buildWithDefaultTimeout();

        HttpResponse<String> response;
        try {
            response =
                    HttpClient.newBuilder()
                            .build()
                            .send(
                                    request,
                                    HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (response.statusCode() == 200) {
            String responseBody = response.body();
            return gson.fromJson(responseBody, returnClass);
        } else {
            System.out.println("Request failed. " + "code: " + response.statusCode() + " url: " + request.uri() + " body: " + response.body());
            return null; //TODO: return Optional<T> from this method?
        }
    }

}
