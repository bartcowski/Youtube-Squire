package com.github.bartcowski.infrastructure.authorization.repository;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Optional;

@Service
public class LocalFileOAuthAccessTokenRepository implements OAuthAccessTokenRepository {

    private static final String ACCESS_TOKEN_FILE_PATH = "./token.txt";

    @Override
    public void saveAccessToken(String accessToken) {
        File accessTokenFile = new File(ACCESS_TOKEN_FILE_PATH);

        if (accessTokenFile.exists()) {
            try (FileWriter writer = new FileWriter(ACCESS_TOKEN_FILE_PATH, false)) {
                writer.write("");
                System.out.println("File content cleared successfully.");
            } catch (IOException e) {
                throw new RuntimeException("Error clearing the file");
            }
        } else {
            try {
                boolean wasFileCreated = accessTokenFile.createNewFile();
                if (!wasFileCreated) {
                    throw new RuntimeException("Access Token File couldn't be created");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try (FileWriter writer = new FileWriter(ACCESS_TOKEN_FILE_PATH)) {
            writer.write(accessToken);
        } catch (IOException e) {
            throw new RuntimeException("Error saving file");
        }
    }

    @Override
    public Optional<String> findAccessToken() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCESS_TOKEN_FILE_PATH))) {
            String accessToken = reader.readLine();
            return Optional.ofNullable(accessToken);
        } catch (IOException e) {
            throw new RuntimeException("Error reading the file");
        }
    }

}
