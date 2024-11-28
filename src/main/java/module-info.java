module com.example.naplo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires gson;
    requires httpclient;
    requires httpcore;

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
}
