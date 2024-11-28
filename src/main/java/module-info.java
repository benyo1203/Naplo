module com.example.naplo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    opens com.example.naplo to javafx.fxml;
    exports com.example.naplo;
}
