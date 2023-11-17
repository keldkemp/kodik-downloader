package keldkemp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Main {
    public static void main(String[] args) throws IOException {
        String defaultFileName = "doramaVideo.ts";
        new File(defaultFileName).createNewFile();
        String fragmentUrl = args[0];

        int i = 1;
        while (true) {
            URL url = new URL(fragmentUrl.replace("{i}", String.valueOf(i)));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            if (con.getResponseCode() != 200) {
                break;
            }

            try (InputStream is = url.openStream()) {
                Files.write(Paths.get(defaultFileName), is.readAllBytes(), StandardOpenOption.APPEND);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            i++;
        }
    }
}