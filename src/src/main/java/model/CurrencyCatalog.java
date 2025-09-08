package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class represents the different currencies as CurrencyCard objects
 * in an ArrayList. It is used to populate the filter combos in the UI
 * for the user to be able to find what they are looking for.
 */
public class CurrencyCatalog {
    private ArrayList<CurrencyCard> catalog = new ArrayList<>();

    public CurrencyCatalog() {}

    public void addToCatalog(CurrencyCard card){
        catalog.add(card);
    }

    public List<CurrencyCard> getCards(){
        return Collections.unmodifiableList(catalog);
    }
}
