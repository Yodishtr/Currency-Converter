package dao;

import java.util.ArrayList;

/**
 * This class represents the logs selected in an array of SelectLogs objects.
 * This will be used by the Service Wiring class to report to the controller
 * to be displayed in the ui
 */
public class SelectLogsList {
    private ArrayList<SelectLogs> logsList = new ArrayList<>();

    public ArrayList<SelectLogs> getLogsList(){
        return logsList;
    }

    public void setLogsList(ArrayList<SelectLogs> logsList){
        this.logsList = logsList;
    }

    @Override
    public String toString(){

        if (logsList.isEmpty()){
            return "";
        } else {
            return logsList.getFirst().toString();
        }
    }

}
