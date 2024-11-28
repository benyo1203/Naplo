package com.example.naplo;

public class DiakJegyTargy {

    private int id;
    private String nev;
    private String osztaly;
    private boolean fiu;
    private String datum;
    private int ertek;
    private String tipus;
    private String targyNev;
    private String kategoria;

    public DiakJegyTargy(int id, String nev, String osztaly, boolean fiu, String datum, int ertek, String tipus, String targyNev, String kategoria) {
        this.id = id;
        this.nev = nev;
        this.osztaly = osztaly;
        this.fiu = fiu;
        this.datum = datum;
        this.ertek = ertek;
        this.tipus = tipus;
        this.targyNev = targyNev;
        this.kategoria = kategoria;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNev() {
        return nev;
    }

    public String getOsztaly() {
        return osztaly;
    }

    public boolean isFiu() {
        return fiu;
    }

    public String getDatum() {
        return datum;
    }

    public int getErtek() {
        return ertek;
    }

    public String getTipus() {
        return tipus;
    }

    public String getTargyNev() {
        return targyNev;
    }

    public String getKategoria() {
        return kategoria;
    }
}
