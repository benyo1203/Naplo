module com.example.naplo {
    requires javafx.controls;
    requires javafx.fxml;
    requires gson;
    requires httpclient;
    requires httpcore;
    requires com.fasterxml.jackson.databind;
    requires java.sql;

    //Ezek a  forexhez kellenek
    opens com.oanda.v20;
    opens com.oanda.v20.account;
    opens com.oanda.v20.pricing;
    opens com.oanda.v20.pricing_common;
    opens com.oanda.v20.order;
    opens com.oanda.v20.instrument;
    opens com.oanda.v20.transaction;
    opens com.oanda.v20.trade;
    exports com.oanda.v20.primitives;
    exports com.oanda.v20.transaction;

    opens com.example.naplo to javafx.fxml;
    exports com.example.naplo;
    exports com.example.naplo.forex;
    exports com.example.naplo.parhuzamos;
    opens com.example.naplo.forex to javafx.fxml;
    opens com.example.naplo.parhuzamos to javafx.fxml;
}
