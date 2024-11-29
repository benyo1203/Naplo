package com.example.naplo.parhuzamos;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

public class ParhuzamosController {

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    private Timer timer1;
    private Timer timer2;

    // Gomb kattintás eseménye
    @FXML
    protected void handleButtonAction() {
        // Időzítő 1 - 1 másodpercenként
        timer1 = new Timer();
        timer1.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Frissíti az első Label-t
                updateLabel(label1, "1 másodperc: " + System.currentTimeMillis() % 1000);
            }
        }, 0, 1000);

        // Időzítő 2 - 2 másodpercenként
        timer2 = new Timer();
        timer2.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Frissíti a második Label-t
                updateLabel(label2, "4 másodperc: " + System.currentTimeMillis() % 1000);
            }
        }, 0, 4000);
    }

    // A Label frissítése JavaFX szálon
    private void updateLabel(Label label, String text) {
        // A JavaFX szálon futtatjuk a label frissítését
        javafx.application.Platform.runLater(() -> label.setText(text));
    }
}
