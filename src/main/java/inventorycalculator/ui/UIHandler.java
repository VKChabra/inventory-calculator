package inventorycalculator.ui;

import inventorycalculator.core.Calculator;
import inventorycalculator.core.ScreenshotCapture;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.stage.FileChooser;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class UIHandler {
    private ScreenshotCapture screenshotCapture;
    private Calculator calculator;

    public UIHandler() {
        this.screenshotCapture = new ScreenshotCapture();
        this.calculator = new Calculator();
    }

    public void start(Stage primaryStage) {
        primaryStage.setTitle("Inventory Calculator");

        Button captureButton = new Button("Capture Screenshot");
        captureButton.setOnAction(e -> {
            try {
                screenshotCapture.captureSelectedArea();
            } catch (Exception ex) {
                showError("Error capturing screenshot: " + ex.getMessage());
            }
        });

        VBox layout = new VBox(10, captureButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 400, 300);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
