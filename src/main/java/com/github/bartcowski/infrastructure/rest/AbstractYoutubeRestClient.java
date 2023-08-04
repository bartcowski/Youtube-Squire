package com.github.bartcowski.infrastructure.rest;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractYoutubeRestClient {

    protected static final String BASE_URL = "https://www.googleapis.com/youtube/v3";

    private final Gson gson = new Gson();

    protected <T> T sendRequest(RestRequestBuilder requestBuilder, Class<T> returnClass) {
        final HttpRequest request = requestBuilder.buildWithDefaultTimeout();

        HttpResponse<String> response = send(request);

        if (response.statusCode() == 200) {
            System.out.println("Single request was successful");
            String responseBody = response.body();
            return gson.fromJson(responseBody, returnClass);
        } else {
            System.out.println("Request failed. Code: " + response.statusCode() +
                    " URL: " + request.uri() +
                    " Body: " + response.body());
            return null; //TODO: return Optional<T> from this method?
        }
    }

    protected <T extends PageableResponse> List<T> sendPageableRequest(
            RestRequestBuilder requestBuilder, Class<T> returnClass) {
        List<T> allResponsePages = new ArrayList<>();
        String nextPageToken = "";
        int requestCounter = 1;

        while (nextPageToken != null) {
            final HttpRequest request =
                    requestBuilder.params(Map.of("pageToken", nextPageToken)).buildWithDefaultTimeout();

            HttpResponse<String> response = send(request);

            if (response.statusCode() == 200) {
                System.out.println("Pageable request no. " + requestCounter++ + " was successful");
                String responseBody = response.body();
                T responsePage = gson.fromJson(responseBody, returnClass);
                nextPageToken = responsePage.getNextPageId();
                allResponsePages.add(responsePage);
            } else {
                System.out.println("Pageable request no. " + requestCounter++ + " failed." +
                        " Code: " + response.statusCode() +
                        " URL: " + request.uri() +
                        " Body: " + response.body());
            }
        }
        return allResponsePages;
    }

    private static HttpResponse<String> send(HttpRequest request) {
        try {
            return HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
