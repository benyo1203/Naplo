package com.example.naplo;

import com.example.naplo.SOAP.letoltes.SOAPletoltes;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane(); // Ez az alkalmazás fő elrendezése
        MenuBar menuBar = new MenuBar();

        // Adatbázis menü
        Menu dbMenu = new Menu("Adatbázis");
        MenuItem olvas = new MenuItem("Olvas");
        dbMenu.getItems().add(olvas);

        MenuItem olvas2 = new MenuItem("Olvas2");
        dbMenu.getItems().add(olvas2);

        MenuItem ir = new MenuItem("Ír");
        dbMenu.getItems().add(ir);

        MenuItem modosit = new MenuItem("Módosít");
        dbMenu.getItems().add(modosit);

        MenuItem torol = new MenuItem("Töröl");
        dbMenu.getItems().add(torol);

        // Forexes feladat
        Menu forexMenu = new Menu("Forex");
        MenuItem szamlainfo = new MenuItem("Számlainformációk");
        MenuItem aktualisArak = new MenuItem("Aktuális árak");
        MenuItem historikusArak = new MenuItem("Histórikus Árak");
        MenuItem pozicioNyitas = new MenuItem("Pozíció Nyitás");
        MenuItem pozicioZaras = new MenuItem("Pozíció Zárás");
        MenuItem nyitottPoziciok = new MenuItem("Nyitott Pozíciók");
        MenuItem parhuzamos = new MenuItem("Párhuzamos");

        forexMenu.getItems().add(szamlainfo);
        forexMenu.getItems().add(aktualisArak);
        forexMenu.getItems().add(historikusArak);
        forexMenu.getItems().add(pozicioNyitas);
        forexMenu.getItems().add(pozicioZaras);
        forexMenu.getItems().add(nyitottPoziciok);
        forexMenu.getItems().add(parhuzamos);

        // Menü hozzáadása a menüsorhoz
        menuBar.getMenus().add(dbMenu);
        menuBar.getMenus().add(forexMenu);



        // A menüsor elhelyezése a fő elrendezés tetején
        root.setTop(menuBar);


        // A fő ablak beállítása
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Java elméleti beadandó");
        primaryStage.setScene(scene);
        primaryStage.show();

        olvas.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/naplo/adatbazis/olvas.fxml"));
                VBox olvasView = loader.load();
                root.setCenter(olvasView); // Az "Olvas" FXML betöltése a főablak közepére
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        olvas2.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/naplo/adatbazis/olvas2.fxml"));
                BorderPane olvas2View = loader.load();  // BorderPane típusú
                root.setCenter(olvas2View);  // Mivel root is BorderPane, így ez működni fog
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        ir.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/naplo/adatbazis/ir.fxml"));
                GridPane irView = loader.load();
                root.setCenter(irView);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        modosit.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/naplo/adatbazis/modosit.fxml"));
                GridPane modositView = loader.load();
                root.setCenter(modositView); // Az "Olvas" FXML betöltése a főablak közepére
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        torol.setOnAction(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/naplo/adatbazis/torol.fxml"));
                VBox torolView = loader.load();
                root.setCenter(torolView); // Az "Olvas" FXML betöltése a főablak közepére
            } catch (Exception e) {
                e.printStackTrace();
            }
        });





        szamlainfo.setOnAction(event -> {
            VBox forexView = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/naplo/forex/szamlainformaciok" +
                        ".fxml"));
                forexView = loader.load(); // Betöltjük az aktuális árak FXML fájlt
            } catch (Exception e) {
                e.printStackTrace();
            }
            root.setCenter(forexView); // Az új UI betöltése a középső részhez
        });
        // Eseménykezelő: Aktuális árak
        aktualisArak.setOnAction(event -> {
            VBox forexView = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/naplo/forex/aktualisArak.fxml"));
                forexView = loader.load(); // Betöltjük az aktuális árak FXML fájlt
            } catch (Exception e) {
                e.printStackTrace();
            }
            root.setCenter(forexView); // Az új UI betöltése a középső részhez
        });

        // Eseménykezelő: Histórikus árak
        historikusArak.setOnAction(event -> {
            VBox historicalView = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(
                        "/com/example/naplo/forex/historikusArak.fxml"));
                historicalView = loader.load(); // Betöltjük a histórikus árak FXML fájlt
            } catch (Exception e) {
                e.printStackTrace();
            }
            root.setCenter(historicalView); // Az új UI betöltése a középső részhez
        });

        pozicioNyitas.setOnAction(event -> {
            VBox pozicioNyitView = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/naplo/forex/pozicioNyitas.fxml"));
                pozicioNyitView = loader.load(); // Betöltjük az aktuális árak FXML fájlt
            } catch (Exception e) {
                e.printStackTrace();
            }
            root.setCenter(pozicioNyitView); // Az új UI betöltése a középső részhez
        });

        pozicioZaras.setOnAction(event -> {
            VBox pozicioZarView = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/naplo/forex/pozicioZaras" +
                        ".fxml"));
                pozicioZarView = loader.load(); // Betöltjük az aktuális árak FXML fájlt
            } catch (Exception e) {
                e.printStackTrace();
            }
            root.setCenter(pozicioZarView); // Az új UI betöltése a középső részhez
        });

        nyitottPoziciok.setOnAction(event -> {
            VBox nyitottPozView = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/naplo/forex/nyitottPoziciok" +
                        ".fxml"));
                nyitottPozView = loader.load(); // Betöltjük az aktuális árak FXML fájlt
            } catch (Exception e) {
                e.printStackTrace();
            }
            root.setCenter(nyitottPozView); // Az új UI betöltése a középső részhez
        });

        parhuzamos.setOnAction(event -> {
            VBox parhuzamosView = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/naplo/parhuzamos" +
                        "/parhuzamos" +
                        ".fxml"));
                parhuzamosView = loader.load(); // Betöltjük az aktuális árak FXML fájlt
            } catch (Exception e) {
                e.printStackTrace();
            }
            root.setCenter(parhuzamosView); // Az új UI betöltése a középső részhez
        });
        // SOAP Kliens Menü
        Menu soapMenu = new Menu("SOAP Kliens");
        MenuItem letoltes = new MenuItem("Letöltés");
        MenuItem letoltes2 = new MenuItem("Letöltés2");
        MenuItem grafikon = new MenuItem("Grafikon");
        soapMenu.getItems().add(letoltes);
        soapMenu.getItems().add(letoltes2);
        soapMenu.getItems().add(grafikon);

        menuBar.getMenus().add(soapMenu);
        root.setTop(menuBar);

        letoltes.setOnAction(event -> {
            new Thread(() -> {
                try {
                    String fajlNev = "MNB.txt"; // A fájl neve
                    SOAPletoltes.mnbArfolyamokLetolt(fajlNev);
                    System.out.println("Az árfolyamok sikeresen letöltve: " + fajlNev);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Hiba történt az adatok letöltése közben.");
                }
            }).start();
        });

        letoltes2.setOnAction(event -> {
            VBox letoltesView = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/naplo/SOAP/letöltés2.fxml"));
                letoltesView = loader.load(); // Betöltjük a letöltés2 FXML fájlt
            } catch (Exception e) {
                e.printStackTrace();
                return; // Hiba esetén ne próbáljunk tovább
            }
            root.setCenter(letoltesView); // Az új UI betöltése a középső részhez
        });

        grafikon.setOnAction(event -> {
            VBox grafikonView = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/naplo/SOAP/grafikon.fxml"));
                grafikonView = loader.load(); // Betöltjük a grafikon FXML fájlt
            } catch (Exception e) {
                e.printStackTrace();
                return; // Hiba esetén ne próbáljunk tovább
            }
            root.setCenter(grafikonView); // Az új UI betöltése a középső részhez
        });
    }




    public static void main(String[] args) {
        launch(args);
    }
}
