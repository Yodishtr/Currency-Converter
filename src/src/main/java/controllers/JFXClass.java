package controllers;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class JFXClass extends Application {
    private final Controller controller = new Controller();

    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // use controller methods inside the eventhandlers for the button
        // use material FX for the buttons (for convert), textBoxes(amount and result),
        // table for displaying conversion,logs, filter combos for currency codes,
        // a toggle - switch - to reverse the currencies
        primaryStage.setTitle("Currency Converter");
        Button convertButton = new Button("Convert");

    }
}
