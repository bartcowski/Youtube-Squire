package com.github.bartcowski.authorization;

import com.github.bartcowski.rest.RestRequestBuilder;

public interface RequestAuthorizer {

    void authorize(RestRequestBuilder requestBuilder);

    boolean isApplicableFor(AuthMethodType authMethodType);

}
