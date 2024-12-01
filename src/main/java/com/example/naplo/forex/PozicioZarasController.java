package com.example.naplo.forex;

import com.oanda.v20.Context;
import com.oanda.v20.ContextBuilder;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.trade.TradeCloseRequest;
import com.oanda.v20.trade.TradeSpecifier;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PozicioZarasController {
    static Context ctx;
    static AccountID accountId;

    @FXML
    private TextArea welcomeText;

    @FXML
    private TextField tradeIdInput;  // TextField a pozíció ID megadásához

    @FXML
    protected void onHelloButtonClick() {
        try {
            ctx = new ContextBuilder(Config.URL)
                    .setToken(Config.TOKEN)
                    .setApplication("StepByStepOrder")
                    .build();
            accountId = Config.ACCOUNTID;

            String tradeId = tradeIdInput.getText();

            if (tradeId != null && !tradeId.isEmpty()) {
                Zárás(tradeId);
            } else {
                welcomeText.setText(welcomeText.getText() + "\nKérjük, add meg a pozíció ID-t!");
            }
        } catch (Exception e) {
            welcomeText.setText(welcomeText.getText() + "\nÁltalános hiba: " + e.getMessage());
            e.printStackTrace();
        }
    }


    void Zárás(String tradeId) {
        welcomeText.setText(welcomeText.getText() + "\n" + "Pozíció zárása folyamatban: " + tradeId);
        try {
            // Trade zárás kérése
            TradeCloseRequest closeRequest = new TradeCloseRequest(accountId, new TradeSpecifier(tradeId));
            var response = ctx.trade.close(closeRequest);

            // Ellenőrzés, hogy a zárás sikeres volt-e
            if (response.getOrderFillTransaction() != null) {
                welcomeText.setText(welcomeText.getText() + "\nPozíció sikeresen lezárva. tradeId: " + tradeId);
            } else if (response.getOrderCancelTransaction() != null) {
                welcomeText.setText(welcomeText.getText() + "\nPozíció zárása meghiúsult. Ok: " + response.getOrderCancelTransaction().getReason());
            } else {
                welcomeText.setText(welcomeText.getText() + "\nPozíció zárása sikertelen. Részletek: " + response.toString());
            }
        } catch (Exception e) {
            welcomeText.setText(welcomeText.getText() + "\nHiba a pozíció zárása közben: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
