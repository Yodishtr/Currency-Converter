package controllers;

import dao.ConversionResult;
import dao.InsertLogs;
import dao.SelectLogs;
import io.github.palexdev.materialfx.controls.*;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.controls.legacy.MFXLegacyTableView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.stage.Popup;
import model.CurrencyCard;
import model.CurrencyCatalog;
import model.CurrencyInfo;
import service.ServiceWiring;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
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
    @FXML private ScrollPane descriptionScrollPane;
    @FXML private Label baseName, baseCode, baseDesc, targetName, targetCode, targetDesc;

    // updates the ui conversion table by using an observable list so that no manual refresh needed.
    private final ObservableList<SelectLogs> conversions = FXCollections.observableArrayList();

    private final ServiceWiring serviceWiring = new ServiceWiring();

    @FXML
    private void initialize() {
        // handling the conversion part
        CurrencyCatalog currencyCatalog = serviceWiring.getCurrencyCatalog();
        fromCombo.setItems(FXCollections.observableArrayList(currencyCatalog.getCards()));
        toCombo.setItems(FXCollections.observableArrayList(currencyCatalog.getCards()));
        ResultField.setEditable(false);
        convertButton.setOnAction(e -> onConvert());
        swapButton.setOnAction(e -> onSwap());

        // handling the table display
        setupConversionTable();
        conversionTable.setItems(conversions);
        loadLogsAsync();

        // listen to changes for the base and target currencies to display currency info in scrollpane
        fromCombo.valueProperty().addListener((observable, oldValue, newValue) -> updateDetailsFrom());
        toCombo.valueProperty().addListener((observable, oldValue, newValue) -> updateDetailsTo());


    }

    private void updateDetailsFrom() {
        String fromComboValue = fromCombo.getValue().getCurrencyCode();

        if (fromComboValue == null){
            baseName.setText("");
            baseCode.setText("");
            baseDesc.setText("Choose both a source and a target currency.");
        }

        CurrencyInfo baseCurrencyInfo = serviceWiring.getCurrencyDesc().get(fromComboValue);

        baseName.setText(baseCurrencyInfo.getCurrencyName());
        baseCode.setText(baseCurrencyInfo.getCurrencyCode());
        baseDesc.setText(baseCurrencyInfo.getCurrencyDescription());

    }

    private void updateDetailsTo() {
        String toComboValue = toCombo.getValue().getCurrencyCode();
        if (toComboValue == null) {
            targetName.setText("");
            targetCode.setText("");
            targetDesc.setText("");
        }

        CurrencyInfo targetCurrencyInfo = serviceWiring.getCurrencyDesc().get(toComboValue);
        targetName.setText(targetCurrencyInfo.getCurrencyName());
        targetCode.setText(targetCurrencyInfo.getCurrencyCode());
        targetDesc.setText(targetCurrencyInfo.getCurrencyDescription());
    }

    private void loadLogsAsync() {
        ioExecutor.submit(() -> {
            try{
                ArrayList<SelectLogs> conversionLogs = serviceWiring.performRetrieval();
                Platform.runLater(() -> {
                    conversions.setAll(conversionLogs);
                });

            } catch(Exception e){
                Platform.runLater(() -> {
                    conversions.clear();
                });
            }
        });
    }

    private void setupConversionTable() {
        // setup the columns for the table. Use lambda expression to retrieve appropriate value for row cell
        // add a comparator to enable filtering by the columns type
        // Base Currency column
        MFXTableColumn<SelectLogs> baseColumn = new MFXTableColumn<>("From", true);
        baseColumn.setRowCellFactory(log -> new MFXTableRowCell<>(SelectLogs::getBaseCurrency));
        baseColumn.setComparator(Comparator.comparing(SelectLogs::getBaseCurrency, Comparator.nullsLast(String::compareTo)));

        // Final currency column
        MFXTableColumn<SelectLogs> finalColumn = new MFXTableColumn<>("To", true);
        finalColumn.setRowCellFactory(log -> new MFXTableRowCell<>(SelectLogs::getFinalCurrency));
        finalColumn.setComparator(Comparator.comparing(SelectLogs::getFinalCurrency, Comparator.nullsLast(String::compareTo)));

        // Amount Column
        MFXTableColumn<SelectLogs> amountColumn = new MFXTableColumn<>("Amount", true);
        amountColumn.setRowCellFactory(log -> new MFXTableRowCell<>(l -> l.getAmount().toPlainString()));
        amountColumn.setComparator(Comparator.comparing(SelectLogs::getAmount, Comparator.nullsLast(BigDecimal::compareTo)));

        // Result Column
        MFXTableColumn<SelectLogs> resultColumn = new MFXTableColumn<>("Result", true);
        resultColumn.setRowCellFactory(log -> new MFXTableRowCell<>(l -> l.getResult().toPlainString()));
        resultColumn.setComparator(Comparator.comparing(SelectLogs::getResult, Comparator.nullsLast(BigDecimal::compareTo)));

        // Timestamp Column
        MFXTableColumn<SelectLogs> timeColumn = new MFXTableColumn<>("Time", true);
        timeColumn.setRowCellFactory(log -> new MFXTableRowCell<>(l -> l.getTimeStamp().toString()));
        timeColumn.setComparator(Comparator.comparing(SelectLogs::getTimeStamp, Comparator.nullsLast(LocalDateTime::compareTo)));

        // add the columns to the conversion log table
        conversionTable.getTableColumns().addAll(baseColumn, finalColumn, amountColumn, resultColumn, timeColumn);
        conversionTable.autosizeColumnsOnInitialization();
    }

    private void onConvert(){
        String sourceCurrency = fromCombo.getValue().getCurrencyCode();
        String targetCurrency = toCombo.getValue().getCurrencyCode();
        String amount = AmountField.getText();

        if (sourceCurrency == null || targetCurrency == null || amount == null || sourceCurrency.isBlank() ||
                targetCurrency.isBlank() || amount.isBlank()) {
            ResultField.setText("");
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
        // does the conversion
        ConversionResult conversionResult = serviceWiring.performConversion(sourceCurrency, collectedAmount,
                targetCurrency);
        ResultField.setText(conversionResult.getFinalAmount().toString());

        // need to be run on a separate thread to prevent UI freezing.
        // separate thread for insertion
        ioExecutor.submit(() -> {
            try{
                serviceWiring.performInsertion(sourceCurrency, targetCurrency, collectedAmount,
                        conversionResult.getFinalAmount());
                javafx.application.Platform.runLater(() -> {
                    Popup popup = new Popup();
                    Label message = new Label("Log saved successfully!");
                    popup.getContent().add(message);
                    Bounds bounds = ResultField.localToScreen(ResultField.getBoundsInLocal());
                    popup.show(ResultField, bounds.getMinX(), bounds.getMaxY());
                    popup.hide();
                });
                loadLogsAsync();

            } catch(Exception e) {
                Platform.runLater(() -> {
                    Popup popup = new Popup();
                    Label message = new Label("Failed to save log!");
                    popup.getContent().add(message);
                    Bounds bounds = ResultField.localToScreen(ResultField.getBoundsInLocal());
                    popup.show(ResultField, bounds.getMinX(), bounds.getMaxY());
                    popup.hide();
                });

            } finally {
                Platform.runLater(() -> {
                    convertButton.setDisable(false);
                    swapButton.setDisable(false);
                });
            }
        });

    }

    private void onSwap() {
        CurrencyCard newSourceCurrency = toCombo.getValue();
        CurrencyCard newTargetCurrency = fromCombo.getValue();
        convertButton.setDisable(true);
        swapButton.setDisable(true);
        toCombo.setValue(newTargetCurrency);
        fromCombo.setValue(newSourceCurrency);

        convertButton.setDisable(false);
        swapButton.setDisable(false);
    }

}
