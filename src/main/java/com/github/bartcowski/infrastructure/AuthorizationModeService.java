package com.github.bartcowski.infrastructure;

import com.github.bartcowski.infrastructure.authorization.AuthMethodType;
import com.github.bartcowski.infrastructure.authorization.AuthorizationSupplier;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorizationModeService {

    private final AppAuthorizationModeProvider authorizationModeProvider;

    private final AuthorizationSupplier authorizationSupplier;

    void setMode() {
        AuthMethodType authMethodType = authorizationModeProvider.getMethodType();
        authorizationSupplier.setCurrentAuthMethodType(authMethodType);
    }
}
