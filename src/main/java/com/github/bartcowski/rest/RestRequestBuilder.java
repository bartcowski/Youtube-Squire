package com.github.bartcowski.rest;

import com.github.bartcowski.authorization.AuthorizationSupplier;

import java.net.URI;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.Map;

import static com.github.bartcowski.util.RestUtils.addParamsToUrl;

public class RestRequestBuilder {

    private static final long DEFAULT_TIMEOUT_IN_SECONDS = 10;

    private final HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();

    private final String basePath;

    private String fullPath;

    RestRequestBuilder(String basePath) {
        this.basePath = basePath;
    }

    RestRequestBuilder path(String path) {
        fullPath = basePath + path;
        return this;
    }

    public RestRequestBuilder params(Map<String, String> params) {
        fullPath = addParamsToUrl(fullPath, params);
        return this;
    }

    public RestRequestBuilder header(String key, String value) {
        requestBuilder.header(key, value);
        return this;
    }

    RestRequestBuilder GET() {
        requestBuilder.GET();
        return this;
    }

    RestRequestBuilder authorize(AuthorizationSupplier authorizationSupplier) {
        authorizationSupplier.authorize(this);
        return this;
    }

    HttpRequest buildWithDefaultTimeout() {
        return requestBuilder
                .uri(URI.create(fullPath))
                .timeout(Duration.ofSeconds(DEFAULT_TIMEOUT_IN_SECONDS))
                .build();
    }

}
