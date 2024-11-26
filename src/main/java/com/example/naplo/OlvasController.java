package com.example.naplo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class OlvasController {

    public TableView<DiakJegyTargy> createTableView() {
        TableView<DiakJegyTargy> tableView = new TableView<>();

        TableColumn<DiakJegyTargy, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<DiakJegyTargy, String> nameColumn = new TableColumn<>("Név");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nev"));

        TableColumn<DiakJegyTargy, String> osztalyColumn = new TableColumn<>("Osztály");
        osztalyColumn.setCellValueFactory(new PropertyValueFactory<>("osztaly"));

        TableColumn<DiakJegyTargy, Boolean> fiuColumn = new TableColumn<>("Fiú");
        fiuColumn.setCellValueFactory(new PropertyValueFactory<>("fiu"));

        TableColumn<DiakJegyTargy, String> datumColumn = new TableColumn<>("Dátum");
        datumColumn.setCellValueFactory(new PropertyValueFactory<>("datum"));

        TableColumn<DiakJegyTargy, Integer> ertekColumn = new TableColumn<>("Érték");
        ertekColumn.setCellValueFactory(new PropertyValueFactory<>("ertek"));

        TableColumn<DiakJegyTargy, String> tipusColumn = new TableColumn<>("Típus");
        tipusColumn.setCellValueFactory(new PropertyValueFactory<>("tipus"));

        TableColumn<DiakJegyTargy, String> targyNevColumn = new TableColumn<>("Tantárgy");
        targyNevColumn.setCellValueFactory(new PropertyValueFactory<>("targyNev"));

        TableColumn<DiakJegyTargy, String> kategoriaColumn = new TableColumn<>("Kategória");
        kategoriaColumn.setCellValueFactory(new PropertyValueFactory<>("kategoria"));

        tableView.getColumns().addAll(idColumn, nameColumn, osztalyColumn, fiuColumn, datumColumn, ertekColumn, tipusColumn, targyNevColumn, kategoriaColumn);

        ObservableList<DiakJegyTargy> data = loadDataFromDatabase();
        tableView.setItems(data);

        return tableView;
    }

    private ObservableList<DiakJegyTargy> loadDataFromDatabase() {
        ObservableList<DiakJegyTargy> data = FXCollections.observableArrayList();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:/c:/users/mbbbb/naplo.db")) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT d.id, d.nev AS diak_nev, d.osztaly, d.fiu, j.datum, j.ertek, j.tipus, t.nev AS targy_nev, t.kategoria " +
                    "FROM Diak d " +
                    "JOIN Jegy j ON d.id = j.diakid " +
                    "JOIN Targy t ON t.id = j.targyid");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nev = resultSet.getString("diak_nev");
                String osztaly = resultSet.getString("osztaly");
                boolean fiu = resultSet.getBoolean("fiu");
                String datum = resultSet.getString("datum");
                int ertek = resultSet.getInt("ertek");
                String tipus = resultSet.getString("tipus");
                String targyNev = resultSet.getString("targy_nev");
                String kategoria = resultSet.getString("kategoria");

                DiakJegyTargy diakJegyTargy = new DiakJegyTargy(id, nev, osztaly, fiu, datum, ertek, tipus, targyNev, kategoria);
                data.add(diakJegyTargy);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

}
