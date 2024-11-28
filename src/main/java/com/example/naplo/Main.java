package com.example.naplo;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
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

        // Menü hozzáadása a menüsorhoz
        menuBar.getMenus().add(dbMenu);

        // Menü eseménykezelő - Olvas

        root.setTop(menuBar); // A menüsor elhelyezése a fő elrendezés tetején

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Java elméleti beadandó");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
