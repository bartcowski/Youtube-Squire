package com.github.bartcowski.infrastructure.authorization;

import com.github.bartcowski.infrastructure.rest.RestRequestBuilder;

public interface RequestAuthorizer {

    void authorize(RestRequestBuilder requestBuilder);

    boolean isApplicableFor(AuthMethodType authMethodType);

}
