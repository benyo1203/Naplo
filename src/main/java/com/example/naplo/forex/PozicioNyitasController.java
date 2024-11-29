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
        // Devizapárok feltöltése
        currencyPairComboBox.getItems().addAll("AUD_USD", "EUR_USD", "GBP_USD", "USD_JPY");

        // Lehetséges mennyiségek (pl. 1-100)
        for (int i = 1; i <= 100; i++) {
            amountComboBox.getItems().add(i);
        }

        // Irányok (vétel/eladás)
        directionComboBox.getItems().addAll("Buy", "Sell");
    }

    @FXML
    protected void onHelloButtonClick() {
        try {
            ctx = new ContextBuilder(Config.URL)
                    .setToken(Config.TOKEN)
                    .setApplication("StepByStepOrder")
                    .build();
            accountId = Config.ACCOUNTID;

            String currencyPair = currencyPairComboBox.getValue();
            Integer amount = amountComboBox.getValue();
            String direction = directionComboBox.getValue();

            if (currencyPair != null && amount != null && direction != null) {
                Nyitás(currencyPair, amount, direction);
                welcomeText.setText(welcomeText.getText() + "\n" + "Done");
            } else {
                welcomeText.setText("Please select all values.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void Nyitás(String currencyPair, int amount, String direction) {
        welcomeText.setText(welcomeText.getText() + "\n" + "Place a Market Order");

        InstrumentName instrument = new InstrumentName(currencyPair);
        try {
            OrderCreateRequest request = new OrderCreateRequest(accountId);
            MarketOrderRequest marketOrderRequest = new MarketOrderRequest();
            marketOrderRequest.setInstrument(instrument);

            // Ha "Buy" a választás, akkor pozitív, ha "Sell", akkor negatív
            int units = direction.equals("Buy") ? amount : -amount;
            marketOrderRequest.setUnits(units);

            request.setOrder(marketOrderRequest);

            OrderCreateResponse response = ctx.order.create(request);
            welcomeText.setText(welcomeText.getText() + "\n" + "tradeId: " + response.getOrderFillTransaction().getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
