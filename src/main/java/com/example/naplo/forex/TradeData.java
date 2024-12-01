package com.example.naplo.forex;

import com.oanda.v20.pricing_common.PriceValue;
import com.oanda.v20.primitives.AccountUnits;
import com.oanda.v20.primitives.DecimalNumber;
import com.oanda.v20.primitives.InstrumentName;
import com.oanda.v20.trade.TradeID;

    public class TradeData {
        private String id;
        private String instrument;
        private String openTime;
        private int units;
        private double price;
        private double unrealizedPL;

        public TradeData(TradeID id, InstrumentName instrument, String openTime, DecimalNumber units, PriceValue price,
                         AccountUnits unrealizedPL) {
            this.id = id.toString();
            this.instrument = instrument.toString();
            this.openTime = openTime;
            this.units = Integer.parseInt(units.toString()); // Konvertálás egész számmá
            this.price = Double.parseDouble(price.toString()); // Konvertálás lebegőpontos számmá
            this.unrealizedPL = Double.parseDouble(unrealizedPL.toString()); // Konvertálás lebegőpontos számmá
        }

        // Getterek
        public String getId() {
            return id;
        }

        public String getInstrument() {
            return instrument;
        }

        public String getOpenTime() {
            return openTime;
        }

        public int getUnits() {
            return units;
        }

        public double getPrice() {
            return price;
        }

        public double getUnrealizedPL() {
            return unrealizedPL;
        }
    }


