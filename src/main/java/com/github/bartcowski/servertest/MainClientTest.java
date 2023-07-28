package com.github.bartcowski.servertest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.Scanner;

import static com.github.bartcowski.util.RestUtils.addParamsToUrl;

public class MainClientTest {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String message = scanner.nextLine();

            Map<String, String> params = Map.of("message", message);
            String urlWithParams = addParamsToUrl("http://localhost:8888/api/test", params);
            URI authURI = URI.create(urlWithParams);

            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(authURI)
                    .build();

            try {
                HttpResponse<String> response =
                        HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());
                System.out.println("Message sent: [" + message + "]\n"
                        + "Response: " + response.statusCode() + " " + response.body());
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
