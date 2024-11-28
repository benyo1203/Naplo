package com.example.naplo;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdatbazisBetoltes {
    private static final String DB_URL = "jdbc:sqlite:your_database.db";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    public List<Diak> loadDiakData() {
        List<Diak> diakList = new ArrayList<>();
        String query = "SELECT id, nev, osztaly, fiu FROM Diak";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Diak diak = new Diak(rs.getInt("id"), rs.getString("nev"), rs.getString("osztaly"), rs.getBoolean("fiu"));
                diakList.add(diak);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return diakList;
    }

    public List<Jegy> loadJegyData() {
        List<Jegy> jegyList = new ArrayList<>();
        String query = "SELECT diakid, datum, ertek, tipus, targyid FROM Jegy";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Jegy jegy = new Jegy(rs.getInt("diakid"), rs.getString("datum"), rs.getInt("ertek"), rs.getString("tipus"), rs.getInt("targyid"));
                jegyList.add(jegy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jegyList;
    }

    public List<Targy> loadTargyData() {
        List<Targy> targyList = new ArrayList<>();
        String query = "SELECT id, nev, kategoria FROM Targy";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Targy targy = new Targy(rs.getInt("id"), rs.getString("nev"), rs.getString("kategoria"));
                targyList.add(targy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return targyList;
    }
}
