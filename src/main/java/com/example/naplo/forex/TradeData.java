package com.example.naplo.forex;

import com.oanda.v20.pricing_common.PriceValue;
import com.oanda.v20.primitives.AccountUnits;
import com.oanda.v20.primitives.DecimalNumber;
import com.oanda.v20.primitives.InstrumentName;
import com.oanda.v20.trade.TradeID;

public class TradeData {
    private TradeID id;
    private InstrumentName instrument;
    private String openTime;
    private DecimalNumber units;
    private PriceValue price;
    private AccountUnits unrealizedPL;

    public TradeData(TradeID id, InstrumentName instrument, String openTime, DecimalNumber units, PriceValue price, AccountUnits unrealizedPL) {
        this.id = id;
        this.instrument = instrument;
        this.openTime = openTime;
        this.units = units;
        this.price = price;
        this.unrealizedPL = unrealizedPL;
    }

    public TradeID getId() {
        return id;
    }

    public InstrumentName getInstrument() {
        return instrument;
    }

    public String getOpenTime() {
        return openTime;
    }

    public DecimalNumber getUnits() {
        return units;
    }

    public PriceValue getPrice() {
        return price;
    }

    public AccountUnits getUnrealizedPL() {
        return unrealizedPL;
    }
}
