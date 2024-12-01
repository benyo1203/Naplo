package com.example.naplo.forex;

import com.oanda.v20.Context;
import com.oanda.v20.ContextBuilder;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.order.MarketOrderRequest;
import com.oanda.v20.order.OrderCreateRequest;
import com.oanda.v20.order.OrderCreateResponse;
import com.oanda.v20.primitives.InstrumentName;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PozicioNyitasController {

    static Context ctx;
    static AccountID accountId;
    @FXML
    private ComboBox<String> currencyPairComboBox;
    @FXML
    private ComboBox<Integer> amountComboBox;
    @FXML
    private ComboBox<String> directionComboBox;
    @FXML
    private TextArea welcomeText;

    @FXML
    protected void initialize() {
        try {
            // Devizapárok feltöltése
            currencyPairComboBox.getItems().addAll("EUR_GBP", "EUR_USD", "GBP_USD", "USD_JPY");

            // Lehetséges mennyiségek (pl. 1-100)
            for (int i = 1; i <= 100; i++) {
                amountComboBox.getItems().add(i);
            }

            // Irányok (vétel/eladás)
            directionComboBox.getItems().addAll("Buy", "Sell");

        } catch (Exception e) {
            e.printStackTrace();
            welcomeText.setText("Initialization error: " + e.getMessage());
        }
    }


    @FXML
    protected void onHelloButtonClick() {
        try {
            String currencyPair = currencyPairComboBox.getValue();
            Integer amount = amountComboBox.getValue();
            String direction = directionComboBox.getValue();

            if (currencyPair == null || amount == null || direction == null) {
                welcomeText.setText("Please select all values.");
                return;
            }

            ctx = new ContextBuilder(Config.URL)
                    .setToken(Config.TOKEN)
                    .setApplication("StepByStepOrder")
                    .build();
            accountId = Config.ACCOUNTID;

            Nyitás(currencyPair, amount, direction);
            welcomeText.setText(welcomeText.getText() + "\n" + "Position opened successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            welcomeText.setText("Error opening position: " + e.getMessage());
        }
    }


    void Nyitás(String currencyPair, int amount, String direction) {
        try {
            welcomeText.setText(welcomeText.getText() + "\n" + "Placing Market Order...");

            InstrumentName instrument = new InstrumentName(currencyPair);
            OrderCreateRequest request = new OrderCreateRequest(accountId);
            MarketOrderRequest marketOrderRequest = new MarketOrderRequest();

            marketOrderRequest.setInstrument(instrument);

            // Units beállítása a Buy/Sell alapján
            int units = direction.equalsIgnoreCase("Buy") ? amount : -amount;
            marketOrderRequest.setUnits(units);

            request.setOrder(marketOrderRequest);

            OrderCreateResponse response = ctx.order.create(request);

            // Részletes válasz kiírása
            if (response.getOrderFillTransaction() != null) {
                welcomeText.setText(welcomeText.getText() + "\n" +
                        "Trade ID: " + response.getOrderFillTransaction().getId());
            } else if (response.getOrderCancelTransaction() != null) {
                welcomeText.setText(welcomeText.getText() + "\n" +
                        "Order was cancelled. Reason: " + response.getOrderCancelTransaction().getReason());
            } else {
                // Részletes hibaüzenet kiírása
                welcomeText.setText(welcomeText.getText() + "\n" +
                        "Order failed. Full response: " + response.toString());
                System.out.println("Response details: " + response);
            }
        } catch (Exception e) {
            welcomeText.setText("Order failed: " + e.getMessage());
            e.printStackTrace();
        }
    }





}
