package com.example.naplo.adatbazis;

import java.util.List;

public class DataService {

    private AdatbazisBetoltes loader;

    public DataService() {
        loader = new AdatbazisBetoltes();
    }

    public void displayData() {
        List<Diak> diakList = loader.loadDiakData();
        List<Jegy> jegyList = loader.loadJegyData();
        List<Targy> targyList = loader.loadTargyData();

        // Az adatokat összekapcsoljuk
        for (Diak diak : diakList) {
            for (Jegy jegy : jegyList) {
                if (jegy.getDiakId() == diak.getId()) {
                    // Keressük meg a tantárgyat a jegyhez
                    for (Targy targy : targyList) {
                        if (jegy.getTargyId() == targy.getId()) {
                            System.out.println("Diák: " + diak.getNev() + ", Osztály: " + diak.getOsztaly() +
                                    ", Jegy: " + jegy.getErtek() + ", Tantárgy: " + targy.getNev() +
                                    ", Kategória: " + targy.getKategoria() + ", Dátum: " + jegy.getDatum());
                        }
                    }
                }
            }
        }
    }
}