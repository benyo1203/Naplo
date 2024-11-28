package com.example.naplo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane(); // Ez az alkalmazás fő elrendezése
        MenuBar menuBar = new MenuBar();

        // Adatbázis menü
        Menu dbMenu = new Menu("Adatbázis");
        MenuItem olvas = new MenuItem("Olvas");
        dbMenu.getItems().add(olvas);

        MenuItem olvas1 = new MenuItem("Olvas1");
        dbMenu.getItems().add(olvas1);

        // Forexes feladat
        Menu forexMenu = new Menu("Forex");
        MenuItem szamlainfo = new MenuItem("Számlainformációk");
        MenuItem aktualisArak = new MenuItem("Aktuális árak");
        MenuItem historikusArak = new MenuItem("Histórikus Árak");
        forexMenu.getItems().add(szamlainfo);
        forexMenu.getItems().add(aktualisArak);
        forexMenu.getItems().add(historikusArak);

        // Menü hozzáadása a menüsorhoz
        menuBar.getMenus().add(dbMenu);
        menuBar.getMenus().add(forexMenu);

        // Betöltjük az FXML fájlt, amely tartalmazza a Forex UI-t
        VBox root1 = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/naplo/szamlainformaciok.fxml"));
            root1 = loader.load(); // Az FXML-ben meghatározott UI-t betölti
        } catch (Exception e) {
            e.printStackTrace();
        }

        // A menüsor elhelyezése a fő elrendezés tetején
        root.setTop(menuBar);
        // Az FXML által létrehozott UI hozzáadása a középső részhez
        root.setCenter(root1);

        // A fő ablak beállítása
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Java elméleti beadandó");
        primaryStage.setScene(scene);
        primaryStage.show();

        aktualisArak.setOnAction(event -> {
            VBox forexView = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/naplo/aktualisArak.fxml"));
                forexView = loader.load(); // Betöltjük az aktuális árak FXML fájlt
            } catch (Exception e) {
                e.printStackTrace();
            }
            root.setCenter(forexView); // Az új UI betöltése a középső részhez
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
