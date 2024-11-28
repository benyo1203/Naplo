package com.example.naplo;

public class Jegy {
    private int diakId;
    private String datum;
    private int ertek;
    private String tipus;
    private int targyId;

    public Jegy(int diakId, String datum, int ertek, String tipus, int targyId) {
        this.diakId = diakId;
        this.datum = datum;
        this.ertek = ertek;
        this.tipus = tipus;
        this.targyId = targyId;
    }

    public int getDiakId() {
        return diakId;
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

    public int getTargyId() {
        return targyId;
    }
}
