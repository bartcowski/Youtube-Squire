package com.github.bartcowski.infrastructure;

import com.github.bartcowski.infrastructure.authorization.AuthMethodType;

public interface AppAuthorizationModeProvider {

    AuthMethodType getMethodType();

}
