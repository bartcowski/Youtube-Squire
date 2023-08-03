package com.github.bartcowski.infrastructure.authorization.repository;

import com.github.bartcowski.infrastructure.authorization.entity.AccessToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Optional;

@Service
public class LocalFileOAuthAccessTokenRepository implements OAuthAccessTokenRepository {

    private static final String ACCESS_TOKEN_FILE_PATH = "./access_token.json";

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void saveAccessToken(AccessToken accessToken) {
        File accessTokenFile = new File(ACCESS_TOKEN_FILE_PATH);

        if (!accessTokenFile.exists()) {
            createFile(accessTokenFile);
        }

        String json = gson.toJson(accessToken);
        try (FileWriter writer = new FileWriter(ACCESS_TOKEN_FILE_PATH, false)) {
            writer.write(json);
        } catch (IOException e) {
            throw new RuntimeException("Error saving access token file");
        }
    }

    @Override
    public Optional<AccessToken> findAccessToken() {
        String json = readFromFile();
        AccessToken accessTokenOrNull = gson.fromJson(json, AccessToken.class);
        return Optional.ofNullable(accessTokenOrNull);
    }

    private void createFile(File accessTokenFile) {
        try {
            boolean wasFileCreated = accessTokenFile.createNewFile();
            if (!wasFileCreated) {
                throw new RuntimeException("Access token file couldn't be created");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String readFromFile() {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(ACCESS_TOKEN_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

}
