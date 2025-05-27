/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ilse_
 */
public class SceneManager {

private static Stage stage;

    // Debes llamar esta vez en la inicialización principal, por ejemplo en tu clase Main
    public static void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

    public static void cambiarEscena(String fxml) {
        try {
            Parent root = FXMLLoader.load(SceneManager.class.getResource("/fxml/" + fxml));
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
        } catch (IOException | NullPointerException e) {
        }
    }    
}
