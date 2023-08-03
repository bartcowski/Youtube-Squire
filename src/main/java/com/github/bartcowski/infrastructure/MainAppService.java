package com.github.bartcowski.infrastructure;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MainAppService {

    private final AppAuthorizationModeService appAuthorizationModeService;

    private final AppRunnerService appRunnerService;

    private final AppCleanupService appCleanupService;

    public void startApp() {
        appAuthorizationModeService.setMode();
        appRunnerService.run();
        appCleanupService.cleanup();
    }

}
