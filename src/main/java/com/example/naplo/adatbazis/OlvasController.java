package com.example.naplo.adatbazis;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class OlvasController {

    @FXML
    private TableView<DiakJegyTargy> dataTable;

    @FXML
    private TableColumn<DiakJegyTargy, String> nameColumn;

    @FXML
    private TableColumn<DiakJegyTargy, String> classColumn;

    @FXML
    private TableColumn<DiakJegyTargy, Integer> gradeColumn;

    @FXML
    private TableColumn<DiakJegyTargy, String> subjectColumn;

    @FXML
    private TableColumn<DiakJegyTargy, String> categoryColumn;

    @FXML
    private TableColumn<DiakJegyTargy, String> dateColumn;

    private AdatbazisBetoltes adatbazisBetoltes = new AdatbazisBetoltes();

    public void initialize() {
        // Oszlopok beállítása
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("nev"));
        classColumn.setCellValueFactory(new PropertyValueFactory<>("osztaly"));
        gradeColumn.setCellValueFactory(new PropertyValueFactory<>("ertek"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("targyNev"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("datum"));

        // Adatok betöltése
        List<DiakJegyTargy> data = loadTableData();
        dataTable.setItems(FXCollections.observableArrayList(data));
    }

    private List<DiakJegyTargy> loadTableData() {
        List<Diak> diakList = adatbazisBetoltes.loadDiakData();
        List<Jegy> jegyList = adatbazisBetoltes.loadJegyData();
        List<Targy> targyList = adatbazisBetoltes.loadTargyData();

        List<DiakJegyTargy> combinedList = new ArrayList<>();

        for (Diak diak : diakList) {
            for (Jegy jegy : jegyList) {
                if (jegy.getDiakId() == diak.getId()) {
                    for (Targy targy : targyList) {
                        if (jegy.getTargyId() == targy.getId()) {
                            combinedList.add(new DiakJegyTargy(
                                    diak.getId(),
                                    diak.getNev(),
                                    diak.getOsztaly(),
                                    diak.getFiu(),
                                    jegy.getDatum(),
                                    jegy.getErtek(),
                                    jegy.getTipus(),
                                    targy.getNev(),
                                    targy.getKategoria()
                            ));
                        }
                    }
                }
            }
        }

        return combinedList;
    }
}
