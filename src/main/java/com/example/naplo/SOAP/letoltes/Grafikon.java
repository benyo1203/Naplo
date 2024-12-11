package com.example.naplo.SOAP.letoltes;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import mnb.MNBArfolyamServiceSoap;
import mnb.MNBArfolyamServiceSoapImpl;

import java.util.List;
import java.util.ArrayList;

public class Grafikon {

    @FXML private TextField valutaTextField1;  // Valuta 1 - TextField
    @FXML private TextField valutaTextField2;  // Valuta 2 - TextField
    @FXML private LineChart<Number, Number> lineChart;
    @FXML private NumberAxis xAxis;
    @FXML private NumberAxis yAxis;
    @FXML private Button frissitesGomb;  // Frissítés gomb

    private MNBArfolyamServiceSoap mnbSoapService;

    public Grafikon() {
        // MNBArfolyamServiceSoap inicializálása
        MNBArfolyamServiceSoapImpl impl = new MNBArfolyamServiceSoapImpl();
        mnbSoapService = impl.getCustomBindingMNBArfolyamServiceSoap(); // A SOAP szolgáltatás megfelelő példányosítása
    }

    @FXML
    public void initialize() {
        // Eseménykezelő a TextField-ekre
        valutaTextField1.setOnAction(event -> frissitGrafikon());
        valutaTextField2.setOnAction(event -> frissitGrafikon());

        // Gomb eseménykezelő
        frissitesGomb.setOnAction(event -> frissitGrafikon());  // A gomb megnyomásakor is frissíti a grafikont
    }

    private void frissitGrafikon() {
        String valuta1 = valutaTextField1.getText().toUpperCase();  // Beírt valuta1, nagybetűs formában
        String valuta2 = valutaTextField2.getText().toUpperCase();  // Beírt valuta2, nagybetűs formában

        // Érvényesítés
        if (valuta1 != null && valuta2 != null && isValidCurrency(valuta1) && isValidCurrency(valuta2)) {
            try {
                // Árfolyamok lekérése (valós SOAP hívások megfelelő implementálása)
                List<Double> arfolyamok = getHistoricalExchangeRates(valuta1, valuta2);  // Mindkét valutát egyszerre küldjük

                // Grafikon törlése
                lineChart.getData().clear();

                // Adatok hozzáadása a grafikonhoz
                XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
                XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
                for (int i = 0; i < arfolyamok.size(); i++) {
                    // Különböző valuták grafikonjainak hozzáadása
                    series1.getData().add(new XYChart.Data<>(i, arfolyamok.get(i))); // első valuta árfolyam
                    series2.getData().add(new XYChart.Data<>(i, arfolyamok.get(i))); // második valuta árfolyam
                }

                series1.setName(valuta1);
                series2.setName(valuta2);

                lineChart.getData().addAll(series1, series2);
            } catch (Exception e) {
                e.printStackTrace();
                // Hiba esetén megfelelő hibaüzenet
            }
        } else {
            // Ha a valuták nem érvényesek, akkor hibaüzenet
            System.err.println("Érvénytelen valuták: " + valuta1 + ", " + valuta2);
        }
    }


    // A SOAP hívás a valódi MNB API-ból
    private List<Double> getHistoricalExchangeRates(String valuta1, String valuta2) {
        List<Double> arfolyamok = new ArrayList<>();
        try {
            // Példa dátumformátum: "YYYY-MM-DD"
            String startDate = "2023-01-01";  // Példa kezdő dátum
            String endDate = "2023-12-31";    // Példa záró dátum
            String currencyNames = valuta1 + "," + valuta2;  // A két valuta neve (összefűzve)

            // SOAP hívás a történeti árfolyamok lekérésére
            String response = mnbSoapService.getExchangeRates(startDate, endDate, currencyNames);

            // XML válasz feldolgozása
            // XMLParser.parseExchangeRates(response, arfolyamok);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Hiba az árfolyamok lekérdezése során.");
        }
        return arfolyamok;
    }



    // A valuta érvényesítése (ha csak három ismert valutát engedélyezünk)
    private boolean isValidCurrency(String valuta) {
        return "USD".equals(valuta) || "EUR".equals(valuta) || "GBP".equals(valuta);
    }
}
