package com.example.naplo.forex;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;

public class HistorikusArakControler {

    @FXML
    private ComboBox<String> currencyPairComboBox;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private LineChart<String, Number> priceChart;

    @FXML
    private TableView<HistoricalData> tableView;

    @FXML
    private TableColumn<HistoricalData, String> dateColumn;

    @FXML
    private TableColumn<HistoricalData, Double> priceColumn;

    private final ObservableList<HistoricalData> historicalData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // ComboBox feltöltése a devizapárokkal
        currencyPairComboBox.setItems(FXCollections.observableArrayList("EUR_USD", "GBP_USD", "USD_JPY"));

        // Táblázat oszlopainak összekötése
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Táblázat adatai
        tableView.setItems(historicalData);
    }

    @FXML
    private void onHelloButtonClick() {
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String currencyPair = currencyPairComboBox.getValue();

        if (startDate != null && endDate != null && currencyPair != null) {
            historicalData.clear();
            priceChart.getData().clear();

            // API hívás az adatok lekéréséhez
            String jsonResponse = fetchHistoricalData(currencyPair, startDate.toString(), endDate.toString());
            if (jsonResponse != null) {
                processJsonResponse(jsonResponse);
            }
        } else {
            System.out.println("Kérlek válassz minden paramétert!");
        }
    }

    private String fetchHistoricalData(String currencyPair, String startDate, String endDate) {
        try {
            // URL építése
            String url = Config.URL + "/v3/instruments/" + currencyPair + "/candles" +
                    "?price=M" + // Midpoint árak lekérése
                    "&granularity=D" + // Napi gyertyák
                    "&from=" + startDate +
                    "&to=" + endDate;

            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + Config.TOKEN);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            } else {
                System.out.println("API hívás hiba: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void processJsonResponse(String jsonResponse) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(jsonResponse);
            JsonNode candles = root.get("candles");

            if (candles != null && candles.isArray()) {
                XYChart.Series<String, Number> series = new XYChart.Series<>();
                series.setName("Valós árfolyamok");

                for (JsonNode node : candles) {
                    String date = node.get("time").asText().substring(0, 10); // Csak a dátum részt vesszük
                    double closePrice = node.get("mid").get("c").asDouble();

                    // Táblázat feltöltése
                    historicalData.add(new HistoricalData(date, closePrice));

                    // Grafikon feltöltése
                    series.getData().add(new XYChart.Data<>(date, closePrice));
                }

                priceChart.getData().add(series);
            } else {
                System.out.println("Nincs adat a megadott időszakra.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class HistoricalData {
        private final String date;
        private final Double price;

        public HistoricalData(String date, Double price) {
            this.date = date;
            this.price = price;
        }

        public String getDate() {
            return date;
        }

        public Double getPrice() {
            return price;
        }
    }
}
