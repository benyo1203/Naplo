package com.example.naplo.SOAP.letoltes;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Letöltés2Controller {
    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextField currenciesField;

    @FXML
    private Button downloadButton;

    @FXML
    public void initialize() {
        downloadButton.setOnAction(event -> {
            String startDate = getFormattedDate(startDatePicker.getValue());
            String endDate = getFormattedDate(endDatePicker.getValue());
            String currencies = currenciesField.getText().isEmpty() ? "EUR" : currenciesField.getText();

            new Thread(() -> {
                try {
                    String fajlNev = "mnb_arfolyamok_kivalasztott.txt";
                    SOAPletoltes.mnbArfolyamokLetoltEgyeni(fajlNev, startDate, endDate, currencies);
                    System.out.println("Letöltés sikeres: " + fajlNev);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Hiba történt a letöltés során.");
                }
            }).start();
        });
    }

    private String getFormattedDate(LocalDate date) {
        if (date == null) {
            return LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        }
        return date.format(DateTimeFormatter.ISO_DATE);
    }
}
