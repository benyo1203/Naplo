module com.example.naplo {
    requires com.google.gson;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires java.sql;
    requires org.apache.httpcomponents.httpcore;
    requires org.apache.httpcomponents.httpclient;
    requires jakarta.activation;
    requires jakarta.xml.ws;
    requires jakarta.xml.bind;
    requires com.sun.xml.bind;
    requires java.desktop;
    requires java.net.http;
    requires org.json;



    opens mnb to com.sun.xml.bind;

    exports com.example.naplo;

    opens com.example.naplo to javafx.base, javafx.fxml, java.sql;


    // Forexhez szükséges nyitások
    opens com.oanda.v20 to jakarta.xml.bind, com.google.gson;
    opens com.oanda.v20.account to jakarta.xml.bind, com.google.gson;
    opens com.oanda.v20.pricing to jakarta.xml.bind, com.google.gson;
    opens com.oanda.v20.pricing_common to jakarta.xml.bind, com.google.gson;
    opens com.oanda.v20.order to jakarta.xml.bind, com.google.gson;
    opens com.oanda.v20.instrument to jakarta.xml.bind;
    opens com.oanda.v20.transaction to jakarta.xml.bind, com.google.gson;
    opens com.oanda.v20.trade to jakarta.xml.bind, com.google.gson;

    exports com.oanda.v20.primitives;
    exports com.oanda.v20.transaction;

    // Exportáljuk a SOAP csomagot
    exports com.example.naplo.forex;
    exports com.example.naplo.parhuzamos;

    opens com.example.naplo.forex to javafx.fxml, jakarta.xml.bind;
    opens com.example.naplo.parhuzamos to javafx.fxml, jakarta.xml.bind;

    // Nyisd meg a com.example.naplo.SOAP.letoltes csomagot a javafx.fxml modul számára
    opens com.example.naplo.SOAP.letoltes to javafx.fxml;

    exports mnb;
    exports com.example.naplo.SOAP.letoltes;
    exports com.example.naplo.adatbazis;
    opens com.example.naplo.adatbazis to jakarta.xml.bind, java.sql, javafx.base, javafx.fxml;
}
