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
        mnbSoapService = impl.getCustomBindingMNBArfolyamServiceSoap(); // A soap szolgáltatás megfelelő példányosítása
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
                // Árfolyamok lekérése (SOAP hívások megfelelő implementálása szükséges)
                List<Double> arfolyamokValuta1 = getHistoricalExchangeRates(valuta1);
                List<Double> arfolyamokValuta2 = getHistoricalExchangeRates(valuta2);

                // Grafikon törlése
                lineChart.getData().clear();

                // Adatok hozzáadása a grafikonhoz
                XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
                XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
                for (int i = 0; i < arfolyamokValuta1.size(); i++) {
                    series1.getData().add(new XYChart.Data<>(i, arfolyamokValuta1.get(i)));
                    series2.getData().add(new XYChart.Data<>(i, arfolyamokValuta2.get(i)));
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

    // A SOAP hívás helyettesítő metódusa (implementáld a saját logikádat)
    private List<Double> getHistoricalExchangeRates(String valuta) {
        // Itt kell implementálni a megfelelő SOAP hívásokat, hogy lekérjük az adott valuta árfolyamait
        // Például:
        // return mnbSoapService.getHistoricalRates(valuta);

        // Ha nem elérhető az MNB API, akkor helyettesítheted a tesztadatokkal:
        return List.of(300.0, 301.0, 302.0, 303.0, 304.0); // Példa adat
    }

    // A valuta érvényesítése (ha csak három ismert valutát engedélyezünk)
    private boolean isValidCurrency(String valuta) {
        return "USD".equals(valuta) || "EUR".equals(valuta) || "GBP".equals(valuta);
    }
}
