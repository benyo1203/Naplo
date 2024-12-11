package com.example.naplo.adatbazis;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Olvas2Controller {
    @FXML
    private TextField nameField;

    @FXML
    private ComboBox<String> classComboBox;

    @FXML
    private RadioButton maleRadioButton;

    @FXML
    private RadioButton femaleRadioButton;

    @FXML
    private CheckBox categoryCheckBox;

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

        // Osztályok betöltése a lenyíló listába
        List<String> classes = adatbazisBetoltes.loadDiakData().stream()
                .map(Diak::getOsztaly)
                .distinct()
                .collect(Collectors.toList());
        classComboBox.setItems(FXCollections.observableArrayList(classes));

        // Alapértelmezett adatok betöltése
        loadFilteredData();
    }

    @FXML
    private void loadFilteredData() {
        List<DiakJegyTargy> data = loadTableData(); // Az adatokat beolvassuk

        // Név szűrése
        if (!nameField.getText().isEmpty()) {
            data = data.stream()
                    .filter(d -> d.getNev().toLowerCase().contains(nameField.getText().toLowerCase()))
                    .collect(Collectors.toList());
        }

        // Osztály szűrése
        if (classComboBox.getValue() != null) {
            data = data.stream()
                    .filter(d -> d.getOsztaly().equals(classComboBox.getValue()))
                    .collect(Collectors.toList());
        }

        // Nemek szerinti szűrés
        if (maleRadioButton.isSelected()) {
            data = data.stream().filter(DiakJegyTargy::isFiu).collect(Collectors.toList());
        } else if (femaleRadioButton.isSelected()) {
            data = data.stream().filter(d -> !d.isFiu()).collect(Collectors.toList());
        }


        // A táblázat frissítése
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
