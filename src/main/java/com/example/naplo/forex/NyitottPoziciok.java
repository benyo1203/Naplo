package com.example.naplo.forex;

import com.oanda.v20.Context;
import com.oanda.v20.ContextBuilder;
import com.oanda.v20.ExecuteException;
import com.oanda.v20.RequestException;
import com.oanda.v20.account.AccountID;
import com.oanda.v20.pricing_common.PriceValue;
import com.oanda.v20.primitives.AccountUnits;
import com.oanda.v20.primitives.DateTime;
import com.oanda.v20.primitives.DecimalNumber;
import com.oanda.v20.primitives.InstrumentName;
import com.oanda.v20.trade.Trade;
import com.oanda.v20.trade.TradeID;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.List;

public class NyitottPoziciok {
    static Context ctx;
    static AccountID accountId;

    @FXML
    private TextArea welcomeText;

    @FXML
    private TableView<TradeData> tradeTable;

    @FXML
    private TableColumn<TradeData, String> idColumn;

    @FXML
    private TableColumn<TradeData, String> instrumentColumn;

    @FXML
    private TableColumn<TradeData, String> openTimeColumn;

    @FXML
    private TableColumn<TradeData, Integer> unitsColumn;

    @FXML
    private TableColumn<TradeData, Double> priceColumn;

    @FXML
    private TableColumn<TradeData, Double> unrealizedPLColumn;

    @FXML
    protected void onHelloButtonClick() {
        try {
            ctx = new ContextBuilder(Config.URL).setToken(Config.TOKEN).setApplication("StepByStepOrder").build();
            accountId = Config.ACCOUNTID;
            NyitottKiír();
            welcomeText.setText(welcomeText.getText() + "\n" +"Done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    void NyitottKiír() throws ExecuteException, RequestException {
        welcomeText.setText(welcomeText.getText() + "\n" + "Nyitott tradek:");
        List<Trade> trades = ctx.trade.listOpen(accountId).getTrades();

        ObservableList<TradeData> tradeDataList = FXCollections.observableArrayList();

        // Pozíciók kiírása
        for (Trade trade : trades) {
            TradeID id = trade.getId();
            InstrumentName instrument = trade.getInstrument();

            // A DateTime típus konvertálása LocalDateTime-ra
            DateTime openTimeDateTime = trade.getOpenTime();
            String openTimeString = openTimeDateTime.toString();
            LocalDateTime openTime = LocalDateTime.parse(openTimeString, DateTimeFormatter.ISO_DATE_TIME);

            DecimalNumber units = trade.getCurrentUnits();
            PriceValue price = trade.getPrice();
            AccountUnits unrealizedPL = trade.getUnrealizedPL();

            tradeDataList.add(new TradeData(id, instrument, openTime.toString(), units, price, unrealizedPL));
        }

        // Az adattáblát feltöltjük
        tradeTable.setItems(tradeDataList);

        // Külön-külön kezeljük a cellákhoz rendelt értékeket
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        instrumentColumn.setCellValueFactory(new PropertyValueFactory<>("instrument"));
        openTimeColumn.setCellValueFactory(new PropertyValueFactory<>("openTime"));
        unitsColumn.setCellValueFactory(new PropertyValueFactory<>("units"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        unrealizedPLColumn.setCellValueFactory(new PropertyValueFactory<>("unrealizedPL"));
    }

}
