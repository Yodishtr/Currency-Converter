package app;

import controllers.Controller;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;


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
        Parent root  = FXMLLoader.load(getClass().getResource("/ui/main.fxml"));
        var scene = new Scene(root, 1200, 700);
        scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
        primaryStage.setTitle("Currency Converter");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
