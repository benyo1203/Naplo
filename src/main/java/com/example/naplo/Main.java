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

        // Menü hozzáadása a menüsorhoz
        menuBar.getMenus().add(dbMenu);

        // Menü eseménykezelő
        olvas.setOnAction(e -> {
            // Eseménykezelő az "Olvas" gombhoz
            OlvasController controller = new OlvasController(); // Példányosítjuk az OlvasController-t
            root.setCenter(controller.createTableView()); // A táblázatot a központi részhez rendeljük
        });

        root.setTop(menuBar); // A menüsor elhelyezése a fő elrendezés tetején

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("JavaFX CRUD Alkalmazás");
        primaryStage.setScene(scene);
        primaryStage.show();

        MenuItem olvas2 = new MenuItem("Olvas szűrve");
        dbMenu.getItems().add(olvas2);

        olvas2.setOnAction(e -> {
            Olvas2Controller controller = new Olvas2Controller();
            root.setCenter(controller.createFilterAndTableView());
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
