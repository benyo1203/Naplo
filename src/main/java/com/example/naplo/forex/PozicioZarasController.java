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

            // A TextField-ből lekérjük a pozíció ID-t
            String tradeId = tradeIdInput.getText();

            if (tradeId != null && !tradeId.isEmpty()) {
                Zárás(tradeId);  // A megadott pozíció ID-val hívjuk meg a Zárás metódust
                welcomeText.setText(welcomeText.getText() + "\n" + "Done");
            } else {
                welcomeText.setText(welcomeText.getText() + "\n" + "Pozíció ID nem lett megadva.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            welcomeText.setText(welcomeText.getText() + "\n" + "Hiba történt.");
        }
    }

    void Zárás(String tradeId) {
        welcomeText.setText(welcomeText.getText() + "\n" + "Zárás: " + tradeId);
        try {
            // A pozíció ID-t átadjuk a TradeCloseRequest konstruktorának
            ctx.trade.close(new TradeCloseRequest(accountId, new TradeSpecifier(tradeId)));
            welcomeText.setText(welcomeText.getText() + "\n" + "Pozíció sikeresen lezárva. tradeId: " + tradeId);
        } catch (Exception e) {
            welcomeText.setText(welcomeText.getText() + "\n" + "Hiba a pozíció zárása közben.");
            throw new RuntimeException(e);
        }
    }
}
