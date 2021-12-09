package de.constellate.nitroapp.utils;

import com.google.gson.JsonObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.OutputStream;
import java.net.URL;

public class API {

    public static void call(String path, JsonObject json) {
        try {
            URL url = new URL("https://api.remastered.nitroapp.de" + path);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "69");
            connection.setDoOutput(true);

            OutputStream stream = connection.getOutputStream();

            stream.write(json.toString().getBytes());
            stream.flush();
            stream.close();

            connection.getInputStream().close();
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
