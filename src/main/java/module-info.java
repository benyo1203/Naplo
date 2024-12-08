module com.example.naplo {
    requires com.google.gson;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires java.sql;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpclient;
    requires javax.jws;
    requires java.xml.ws;
    requires java.xml.bind;


    opens com.example.naplo.SOAP.mnb to javax.activation;


    exports com.example.naplo;

    // Forexhez kellenek
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


  // Exportáljuk a SOAP csomagot

    exports com.example.naplo.forex;
    exports com.example.naplo.parhuzamos;
    opens com.example.naplo.forex to javafx.fxml;
    opens com.example.naplo.parhuzamos to javafx.fxml;
}
