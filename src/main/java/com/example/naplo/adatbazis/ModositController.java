package com.example.naplo.adatbazis;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModositController {

    @FXML
    private ComboBox<Integer> idComboBox;

    @FXML
    private TextField nevTextField;

    @FXML
    private TextField osztalyTextField;

    @FXML
    private Label successLabel;

    private static final String DB_URL = "jdbc:sqlite:naplo.db";
    private static final String USER = "user";
    private static final String PASSWORD = "";

    @FXML
    public void initialize() {
        // Rekordok azonosítóinak betöltése a ComboBox-ba
        loadRecordIds();
    }

    private void loadRecordIds() {
        String query = "SELECT id FROM Diak";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                idComboBox.getItems().add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleUpdateRecord() {
        Integer selectedId = idComboBox.getValue();
        String nev = nevTextField.getText();
        String osztaly = osztalyTextField.getText();

        if (selectedId == null || nev.isEmpty() || osztaly.isEmpty()) {
            successLabel.setText("Minden mező kitöltése szükséges!");
            return;
        }

        // Az adatbázis rekord frissítése
        boolean success = updateRecordInDatabase(selectedId, nev, osztaly);

        if (success) {
            successLabel.setText("Sikeresen módosítva!");
        } else {
            successLabel.setText("Hiba történt a módosítás közben!");
        }
    }

    private boolean updateRecordInDatabase(int id, String nev, String osztaly) {
        String query = "UPDATE Diak SET nev = ?, osztaly = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, nev);
            pstmt.setString(2, osztaly);
            pstmt.setInt(3, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
