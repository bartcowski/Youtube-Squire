package com.github.bartcowski.infrastructure;

import com.github.bartcowski.authorization.AuthMethodType;

public interface AppAuthorizationModeProvider {

    AuthMethodType getMethodType();

}
