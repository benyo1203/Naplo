package com.example.naplo.adatbazis;

public class Targy {
    private int id;
    private String nev;
    private String kategoria;

    public Targy(int id, String nev, String kategoria) {
        this.id = id;
        this.nev = nev;
        this.kategoria = kategoria;
    }

    public int getId() {
        return id;
    }

    public String getNev() {
        return nev;
    }

    public String getKategoria() {
        return kategoria;
    }
}
