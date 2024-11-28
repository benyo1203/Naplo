package com.example.naplo;

import com.oanda.v20.ContextBuilder;
import com.oanda.v20.pricing.ClientPrice;
import com.oanda.v20.pricing.PricingGetRequest;
import com.oanda.v20.pricing.PricingGetResponse;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.Context;

import java.util.List;
import java.util.ArrayList;

public class AktualisArakController {

    @FXML
    private Label welcomeText;

    @FXML
    private ComboBox<String> firstCurrencyComboBox;

    @FXML
    private ComboBox<String> secondCurrencyComboBox;

    @FXML
    protected void initialize() {
        // Devizák listájának hozzáadása mindkét ComboBox-hoz
        List<String> currencies = new ArrayList<>();
        currencies.add("EUR");
        currencies.add("USD");
        currencies.add("JPY");
        currencies.add("GBP");
        currencies.add("CHF");

        firstCurrencyComboBox.setItems(FXCollections.observableArrayList(currencies));
        secondCurrencyComboBox.setItems(FXCollections.observableArrayList(currencies));
    }

    @FXML
    protected void onHelloButtonClick() {
        try {
            String firstCurrency = firstCurrencyComboBox.getValue();
            String secondCurrency = secondCurrencyComboBox.getValue();

            if (firstCurrency == null || secondCurrency == null) {
                welcomeText.setText("Kérlek válassz mindkét devizát.");
                return;
            }

            // Ellenőrizni, hogy a két deviza megegyezik-e
            if (firstCurrency.equals(secondCurrency)) {
                welcomeText.setText("Azonos devizák: Az árfolyam 1.0");
                return;
            }

            // Devizapár összeállítása
            String selectedPair = firstCurrency + "-" + secondCurrency;

            Context ctx = new ContextBuilder(Config.URL)
                    .setToken(Config.TOKEN)
                    .setApplication("PricePolling")
                    .build();

            AccountID accountId = Config.ACCOUNTID;
            List<String> instruments = new ArrayList<>();
            instruments.add(selectedPair);

            PricingGetRequest request = new PricingGetRequest(accountId, instruments);
            PricingGetResponse resp = ctx.pricing.get(request);

            if (resp.getPrices().isEmpty()) {
                welcomeText.setText("Nem található ár ehhez a devizapárhoz.");
                return;
            }

            ClientPrice price = resp.getPrices().get(0);
            String askPrice = price.getAsks().get(0).getPrice().toString();
            welcomeText.setText("Aktuális ár (" + selectedPair + "): " + askPrice);
        } catch (Exception e) {
            welcomeText.setText("Hiba történt az árak lekérdezésekor.");
            e.printStackTrace();
        }
    }
}
