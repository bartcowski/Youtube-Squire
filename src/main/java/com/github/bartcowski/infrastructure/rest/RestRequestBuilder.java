package com.github.bartcowski.infrastructure.rest;

import com.github.bartcowski.infrastructure.authorization.AuthorizationSupplier;

import java.net.URI;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.Map;

import static com.github.bartcowski.util.RestUtils.addParamsToUrl;
import static com.github.bartcowski.util.RestUtils.createRequestBody;

public class RestRequestBuilder {

    private static final long DEFAULT_TIMEOUT_IN_SECONDS = 10;

    private final HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();

    private final String basePath;

    private String fullPath;

    public RestRequestBuilder(String basePath) {
        this.basePath = basePath;
    }

    public RestRequestBuilder path(String path) {
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

    public RestRequestBuilder GET() {
        requestBuilder.GET();
        return this;
    }

    public RestRequestBuilder POST(Map<String, String> bodyData) {
        requestBuilder.POST(HttpRequest.BodyPublishers.ofString(createRequestBody(bodyData)));
        return this;
    }

    public RestRequestBuilder authorize(AuthorizationSupplier authorizationSupplier) {
        authorizationSupplier.authorize(this);
        return this;
    }

    public HttpRequest buildWithDefaultTimeout() {
        return requestBuilder
                .uri(URI.create(fullPath))
                .timeout(Duration.ofSeconds(DEFAULT_TIMEOUT_IN_SECONDS))
                .build();
    }

}
