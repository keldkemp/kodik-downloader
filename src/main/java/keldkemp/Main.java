package keldkemp;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;

public class Main {
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(30L))
            .build();

    public static void main(String[] args) {
        try {
            String defaultFileName = "doramaVideo.ts";
            new File(defaultFileName).createNewFile();
            String fragmentUrl = args[0];

            int i = 1;
            while (true) {
                HttpRequest request = HttpRequest.newBuilder()
                        .GET()
                        .uri(new URI(fragmentUrl.replace("{i}", String.valueOf(i))))
                        .timeout(Duration.ofSeconds(30L))
                        .build();
                HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
                if (response.statusCode() != 200) {
                    break;
                }
                Files.write(Paths.get(defaultFileName), response.body(), StandardOpenOption.APPEND);
                i++;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            httpClient.close();
        }
    }
}