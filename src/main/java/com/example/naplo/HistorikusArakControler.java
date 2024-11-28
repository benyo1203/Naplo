package com.example.naplo;

import com.oanda.v20.ContextBuilder;
import com.oanda.v20.instrument.Candlestick;
import com.oanda.v20.instrument.InstrumentCandlesRequest;
import com.oanda.v20.instrument.InstrumentCandlesResponse;
import com.oanda.v20.primitives.InstrumentName;
import javafx.fxml.FXML;
import com.oanda.v20.Context;
import javafx.scene.control.TextArea;
import static com.oanda.v20.instrument.CandlestickGranularity.H1;

public class HistorikusArakControler {
    @FXML
    private TextArea welcomeText;
    @FXML
    protected void onHelloButtonClick() {
        try {
            Context ctx = new
                    ContextBuilder(Config.URL).setToken(Config.TOKEN).setApplication("HistorikusAdatok").build();
            InstrumentCandlesRequest request = new InstrumentCandlesRequest(new
                    InstrumentName("EUR_USD"));
            request.setGranularity(H1);
            request.setCount(10L);
            InstrumentCandlesResponse resp = ctx.instrument.candles(request);
            for(Candlestick candle: resp.getCandles())
                welcomeText.setText(welcomeText.getText() + "\n" + candle.toString());
            for(Candlestick candle: resp.getCandles())
                welcomeText.setText(welcomeText.getText() + "\n" +
                        candle.getTime()+"\t"+candle.getMid().getC());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
