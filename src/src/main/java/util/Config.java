package util;

import java.io.InputStream;
import java.util.Properties;

/**
 * This class represents the configurations in this app.
 * It provides us with the api key
 */
public class Config {
    private static Properties props = new Properties();

    static {
        try{
            InputStream inputStream = Config.class.getClassLoader().getResourceAsStream("config.properties");
            props.load(inputStream);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getKey(String key){
        return props.getProperty(key);
    }
}
