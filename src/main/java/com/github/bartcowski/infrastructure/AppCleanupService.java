package com.github.bartcowski.infrastructure;

import com.github.bartcowski.infrastructure.authorization.server.OAuthCallbackServer;
import org.springframework.stereotype.Service;

@Service
class AppCleanupService {

    void cleanup() {
        OAuthCallbackServer.stop();
    }

}
