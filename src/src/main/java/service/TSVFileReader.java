package service;

import model.CurrencyInfo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the reader for the tsv file in the app. It is used
 * to parse the currencies in the currencies.tsv file and thus store it all in a map
 * with key = currency Code and value = Currency Info. This class is used by the
 * ServiceWiring class.
 */
public class TSVFileReader {
    private Map<String, CurrencyInfo> currencyInfoMap = new HashMap<>();

    public TSVFileReader(){
        try{
            InputStream input = getClass().getResourceAsStream("/currencies.tsv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

        } catch (Exception e){
            System.out.println("Could not load currency info. Error: " + e.getMessage());
        }
    }
}
