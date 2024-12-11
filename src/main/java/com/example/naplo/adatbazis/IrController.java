package com.example.naplo.adatbazis;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class IrController {

    @FXML
    private TextField nevTextField;

    @FXML
    private TextField osztalyTextField;

    @FXML
    private TextField fiuTextField;

    @FXML
    private Label successLabel; // A siker üzenetet megjelenítő Label

    private static final String DB_URL = "jdbc:sqlite:adatok.db";
    private static final String USER = "user";
    private static final String PASSWORD = "";

    @FXML
    public void handleAddRecord() {
        String nev = nevTextField.getText();
        String osztaly = osztalyTextField.getText();
        boolean fiu = Boolean.parseBoolean(fiuTextField.getText());

        // Az adatbázisba való hozzáadás
        boolean success = addDiakToDatabase(nev, osztaly, fiu);

        // Ha sikeres a hozzáadás, frissítjük a siker üzenetet
        if (success) {
            successLabel.setText("Sikeresen hozzáadva!");
        } else {
            successLabel.setText("Hiba történt!");
        }
    }

    private boolean addDiakToDatabase(String nev, String osztaly, boolean fiu) {
        String query = "INSERT INTO Diak (nev, osztaly, fiu) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nev);
            pstmt.setString(2, osztaly);
            pstmt.setBoolean(3, fiu);

            pstmt.executeUpdate();
            return true; // Ha sikerült, visszatérünk true-val
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Ha hiba történt, false-t adunk vissza
        }
    }
}
