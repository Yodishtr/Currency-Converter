package util;


import com.fasterxml.jackson.databind.ObjectMapper;
import dao.RateResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

/**
 * This class represents the connection established to the FreeExchangeAPi endpoint.
 * It provides us with the live currency rates available.
 */
public class APIClient {
    private final String baseUrl = "https://api.exchangeratesapi.io/v1/latest?access_key=";
    private final String apiKey = Config.getKey("api.key");
    private final ObjectMapper mapper = new ObjectMapper();

    public APIClient() {
        if (apiKey == null || apiKey.isEmpty()){
            throw new IllegalStateException("API Key is not set");
        }
    }

    public RateResponse fetchRate() {
        String finalUrl = baseUrl + apiKey;
        RateResponse rateResponse = null;
        try {
            URL url = new URL(finalUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int status = connection.getResponseCode();
            boolean stream;
            if (status >= 400){
                stream = true;
            } else {
                stream = false;
            }
            String responseBody = readResponse(connection, stream);
            rateResponse = mapper.readValue(responseBody, RateResponse.class);

        } catch (java.net.MalformedURLException e) {
            System.err.println("Bad URL: " + finalUrl);
            return null;
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            System.err.println("Failed to parse rates JSON: " + e.getMessage());
            return null;
        } catch (IOException e) {
            System.err.println("I/O error calling rates API: " + e.getMessage());
            return null;
        }
        return rateResponse;

    }

    private static String readResponse(HttpURLConnection connection, boolean errorStream) throws IOException {
        InputStream inputStream;
        if (errorStream){
            inputStream = connection.getErrorStream();
        } else {
            inputStream = connection.getInputStream();
        }
        if (inputStream == null){
            return "";
        }
        StringBuilder stringOutput = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                stringOutput.append(line);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return stringOutput.toString();
    }



}
