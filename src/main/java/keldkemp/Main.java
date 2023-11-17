package keldkemp;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Main {
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        String defaultFileName = "doramaVideo.ts";
        new File(defaultFileName).createNewFile();
        String fragmentUrl = args[0];

        int i = 1;
        while (true) {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(new URI(fragmentUrl.replace("{i}", String.valueOf(i))))
                    .build();
            HttpResponse<byte[]> response = httpClient.send(request, HttpResponse.BodyHandlers.ofByteArray());
            if (response.statusCode() != 200) {
                break;
            }
            Files.write(Paths.get(defaultFileName), response.body(), StandardOpenOption.APPEND);
            i++;
        }
    }
}