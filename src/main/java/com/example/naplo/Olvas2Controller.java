package com.example.naplo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Olvas2Controller {

    public VBox createFilterAndTableView() {
        // Űrlap a szűréshez
        GridPane form = new GridPane();
        form.setPadding(new Insets(10));
        form.setHgap(10);
        form.setVgap(10);

        Label nevLabel = new Label("Név:");
        TextField nevField = new TextField();

        Label osztalyLabel = new Label("Osztály:");
        TextField osztalyField = new TextField();

        Label fiuLabel = new Label("Fiú:");
        TextField fiuField = new TextField();

        Label hasOrdersLabel = new Label("Rendelések:");
        CheckBox hasOrdersCheckbox = new CheckBox("Van rendelés");

        Button filterButton = new Button("Szűrés");

        form.add(nevLabel, 0, 0);
        form.add(nevField, 1, 0);
        form.add(osztalyLabel, 0, 1);
        form.add(osztalyField, 1, 1);
        form.add(hasOrdersLabel, 0, 2);
        form.add(hasOrdersCheckbox, 1, 2);
        form.add(filterButton, 1, 3);

        // Táblázat az eredményekhez
        TableView<Diak> tableView = new TableView<>();
        TableColumn<Diak, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Diak, String> nameColumn = new TableColumn<>("Név");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nev"));

        TableColumn<Diak, String> osztalyColumn = new TableColumn<>("Osztály");
        osztalyColumn.setCellValueFactory(new PropertyValueFactory<>("osztaly"));

        TableColumn<Diak, String> fiuColumn = new TableColumn<>("Fiú");
        fiuColumn.setCellValueFactory(new PropertyValueFactory<>("fiu"));

        tableView.getColumns().addAll(idColumn, nameColumn, osztalyColumn, fiuColumn);

        // Szűrési logika
        filterButton.setOnAction(e -> {
            ObservableList<Diak>
                    filteredDiaks = filterUsers(nevField.getText(), osztalyField.getText(),
                    hasOrdersCheckbox.isSelected());
            tableView.setItems(filteredDiaks);
        });

        // Fő elrendezés
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(form, tableView);

        return layout;
    }

    private ObservableList<Diak> filterUsers(String name, String email, boolean hasOrders) {
        ObservableList<Diak> diaks = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:/c:/users/mbbbb/naplo.db")) {
            String query = "SELECT u.id, u.name, u.email " +
                    "FROM Users u " +
                    (hasOrders ? "JOIN Orders o ON u.id = o.user_id " : "") +
                    "WHERE u.name LIKE ? AND u.email LIKE ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, "%" + name + "%");
            statement.setString(2, "%" + email + "%");

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String diakNev = resultSet.getString("nev");
                String diakOsztaly = resultSet.getString("osztaly");
                boolean diakFiu = resultSet.getBoolean("fiu");
                diaks.add(new Diak(id, diakNev, diakOsztaly, diakFiu));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diaks;
    }
}
