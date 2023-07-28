package com.github.bartcowski.infrastructure;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MainAppService {

    private final AuthorizationModeService authorizationModeService;

    private final AppRunnerService appRunnerService;

    public void startApp() {
        authorizationModeService.setMode();
        appRunnerService.run();
    }

}
