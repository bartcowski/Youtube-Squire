package com.github.bartcowski.ui;

import com.github.bartcowski.authorization.AuthMethodType;
import com.github.bartcowski.infrastructure.AppAuthorizationModeProvider;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
class TerminalAppAuthorizationModeProvider implements AppAuthorizationModeProvider {

    @Override
    public AuthMethodType getMethodType() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("""
                Choose application mode:
                1. readonly (api key authorization)
                2. extended (OAuth 2.0 authorization)""");

        System.out.print("> ");
        int chosenMode = scanner.nextInt();

        if (chosenMode == 1) {
            return AuthMethodType.API_KEY;
        } else if (chosenMode == 2) {
            return AuthMethodType.O_AUTH;
        } else {
            throw new RuntimeException("Invalid app mode chosen");
        }
    }
}
