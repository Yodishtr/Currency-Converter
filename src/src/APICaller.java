import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpClient;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.Map;



public class APICaller {


    public APICaller(){
    }

    public double get_conversion_rate(String user_current_currency, String requested_currency){
        String api_key = "8af6ed176ecd85d2041ae78c";
        String url = "https://v6.exchangerate-api.com/v6/" + api_key + "/latest/" + user_current_currency;
        try{
        URL final_url = new URL(url);
        HttpURLConnection connection_request = (HttpURLConnection) final_url.openConnection();
        connection_request.connect();

        JsonParser parser = new JsonParser();
        JsonElement json_root = parser.parse(new InputStreamReader(connection_request.getInputStream()));
        JsonObject json_obj = json_root.getAsJsonObject();

        String request_result = json_obj.get("result").getAsString();
        if (request_result.equals("success")) {
            JsonObject conversion_rates = json_obj.getAsJsonObject("conversion_rates");
            double exchange_rate = conversion_rates.get(requested_currency).getAsDouble();
            return exchange_rate;
        }
    } catch (Exception e){
            System.out.println("Url connection failed");
        }
        return 0.0;
    }
}
