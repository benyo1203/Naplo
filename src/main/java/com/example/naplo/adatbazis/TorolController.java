package com.example.naplo.adatbazis;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.sql.*;

public class TorolController {

    @FXML
    private ComboBox<String> recordComboBox;  // ComboBox a rekordok azonosítóinak tárolására

    @FXML
    private Button deleteButton;  // A gomb a rekord törlésére

    @FXML
    private Label resultLabel;  // A Label a törlés eredményének megjelenítésére

    // Az adatok (például rekordok azonosítói)
    private ObservableList<String> recordIds = FXCollections.observableArrayList();

    // Az adatbázis URL-je és egyéb beállítások
    private static final String DB_URL = "jdbc:sqlite:naplo.db";
    private static final String USER = "user";
    private static final String PASSWORD = "";

    // Ez a metódus a ComboBox inicializálásáért felelős
    @FXML
    public void initialize() {
        // Betöltjük az adatokat az adatbázisból
        loadRecordIds();
        recordComboBox.setItems(recordIds);  // Rekordok azonosítói hozzáadása a ComboBoxhoz
        deleteButton.setOnAction(event -> deleteRecord());  // Gombnyomás esemény hozzáadása
    }

    // Rekordok azonosítóinak betöltése az adatbázisból
    private void loadRecordIds() {
        String query = "SELECT id FROM Diak"; // Feltételezve, hogy a "Diak" táblából szeretnénk az id-ket lekérdezni

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Végigiterálunk az adatbázis rekordjain
            while (rs.next()) {
                // Az id-k hozzáadása a recordIds listához
                recordIds.add(rs.getString("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Rekord törlésének logikája
    private void deleteRecord() {
        String selectedId = recordComboBox.getValue();  // A kiválasztott rekord azonosítója

        if (selectedId != null) {
            // A rekord törlésének logikája itt történik (pl. adatbázis művelet)
            // Törlés logikája adatbázisból
            String deleteQuery = "DELETE FROM Diak WHERE id = ?";
            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                 PreparedStatement pstmt = conn.prepareStatement(deleteQuery)) {

                pstmt.setString(1, selectedId);
                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    resultLabel.setText("A rekord (" + selectedId + ") sikeresen törlésre került.");
                } else {
                    resultLabel.setText("A rekord törlése nem sikerült.");
                }
            } catch (SQLException e) {
                resultLabel.setText("Hiba történt a törlés során.");
                e.printStackTrace();
            }
        } else {
            resultLabel.setText("Nincs kiválasztva rekord.");
        }
    }
}
