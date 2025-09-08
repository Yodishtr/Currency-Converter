package controllers;

import dao.ConversionResult;
import dao.InsertLogs;
import dao.SelectLogs;
import io.github.palexdev.materialfx.controls.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import model.CurrencyCard;
import model.CurrencyCatalog;
import service.ServiceWiring;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Controller {
    // An executor object which will allow background processes to work without freezing the UI
    // kinda similar to fork in C but localized to JavaFX and allows me to run the conversion
    // and the insertion
    private final ExecutorService ioExecutor = Executors.newFixedThreadPool(4, r -> {
        Thread t = new Thread(r, "indentured labourers code-like");
        t.setDaemon(true);
        return t;
    });

    // Left Panel: contains table of conversion logs
    @FXML private MFXTableView<SelectLogs> conversionTable;


    // Top Right panel: contains the textfields and buttons for conversions
    @FXML private MFXFilterComboBox<CurrencyCard> fromCombo;
    @FXML private MFXTextField AmountField;
    @FXML private MFXFilterComboBox<CurrencyCard> toCombo;
    @FXML private MFXTextField ResultField;
    @FXML private MFXButton convertButton;
    @FXML private MFXButton swapButton;


    // Centre Right Panel: contains the line chart to display the historical data for the currency
    @FXML private LineChart<String, Number> historicalGraph;
    @FXML private CategoryAxis dateAxis;
    @FXML private NumberAxis rateAxis;
    @FXML private Label emptyGraphMsg;
    @FXML private MFXProgressSpinner loadingSpinner;

    // updates the ui conversion table by using an observable list so that no manual refresh needed.
    private final ObservableList<SelectLogs> conversions = FXCollections.observableArrayList();

    private final ServiceWiring serviceWiring = new ServiceWiring();

    @FXML
    private void initialize() {
        // handling the conversion part
        CurrencyCatalog currencyCatalog = serviceWiring.getCurrencyCatalog();
        fromCombo.setItems(FXCollections.observableArrayList(currencyCatalog.getCards()));
        toCombo.setItems(FXCollections.observableArrayList(currencyCatalog.getCards()));
        convertButton.setOnAction(e -> onConvert());


    }

    private void onConvert() {
        String sourceCurrency = fromCombo.getValue().getCurrencyCode();
        String targetCurrency = toCombo.getValue().getCurrencyCode();
        String amount = AmountField.getText();

        if (sourceCurrency == null || targetCurrency == null || amount == null || sourceCurrency.isBlank() ||
                targetCurrency.isBlank() || amount.isBlank()) {
            ResultField.setText("Select currencies and input an amount");
            return;
        }
        BigDecimal collectedAmount;
        try{
            collectedAmount = new BigDecimal(amount.replace(",", "").trim());

        }catch(Exception e){
            ResultField.setText("Enter a valid amount");
            return;
        }
        // prevents repeated attempts for conversions and trying to swap while converting.
        convertButton.setDisable(true);
        swapButton.setDisable(true);

        Task<ConversionResult> task = new Task<>() {
            @Override
            protected ConversionResult call() throws IOException {
                ConversionResult conversionResult = serviceWiring.performConversion(sourceCurrency, collectedAmount,
                        targetCurrency);
                InsertLogs insertLog = new InsertLogs();
                insertLog.setBaseCurrency(sourceCurrency);
                insertLog.setEndCurrency(targetCurrency);
                insertLog.setAmount(collectedAmount);
                insertLog.setResult(conversionResult.getFinalAmount());
                return conversionResult;
            }
        };

        task.setOnSucceeded(e -> {
            ConversionResult result = task.getValue();
            ResultField.setText(result.getFinalAmount().toString());
            convertButton.setDisable(false);
            swapButton.setDisable(false);
        });

        task.setOnFailed(e -> {
            Throwable ex = task.getException();
            ResultField.setText("Conversion failed");
            convertButton.setDisable(false);
            swapButton.setDisable(false);
        });

        ioExecutor.submit(task);

    }

}
