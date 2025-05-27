package main.sistemagestionzamira;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import main.sistemagestionzamira.model.ConexionDB;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));
        Parent root = loader.load();
        
        scene = new Scene(root, 1200, 1000);
        scene.getStylesheets().add(getClass().getResource("/styles/estilos.css").toExternalForm());
        stage.setTitle("Sistema de Gestión Zamira");
        stage.setScene(scene);
        stage.show();
        
        // Tamaño deseado para la ventana
        double width = 800;
        double height = 600;

        stage.setWidth(width);
        stage.setHeight(height);

        // Obtener tamaño de la pantalla
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        // Posicionar la ventana en el centro
        double x = (screenBounds.getWidth() - width) / 2;
        double y = (screenBounds.getHeight() - height) / 2;

        stage.setX(x);
        stage.setY(y);

        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
    public void insertarDatosPrueba() {
        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("INSERT INTO USUARIO VALUES (5, 'Carlos Ramírez'), (6, 'Lucía Martínez')");
            stmt.executeUpdate("INSERT INTO PRODUCTO VALUES (5, 'Monitor', 299.99), (6, 'Mouse inalámbrico', 25.50)");
            stmt.executeUpdate("INSERT INTO PEDIDO VALUES (4, 5, CURRENT_TIMESTAMP, 'confirmado', 325.49)");
            stmt.executeUpdate("INSERT INTO DETALLE_PEDIDO VALUES " +
                    "(5, 4, 5, 1, 299.99), " +
                    "(6, 4, 6, 1, 25.50)");
            stmt.executeUpdate("INSERT INTO PAGO VALUES (4, 4, 'PayPal', 325.49, 'pendiente')");
            stmt.executeUpdate("INSERT INTO INVENTARIO VALUES " +
                    "(5, 5, 'salida', 1, CURRENT_TIMESTAMP)," +
                    "(6, 6, 'salida', 1, CURRENT_TIMESTAMP)");
            stmt.executeUpdate("INSERT INTO ENVIO VALUES " +
                    "(4, 4, 'Entrega estándar', 'Av. Libertad 456', 'preparando', DATE_ADD(CURRENT_DATE, INTERVAL 5 DAY))");
            stmt.executeUpdate("INSERT INTO NOTIFICACION VALUES " +
                    "(4, 5, 'Tu pedido será enviado pronto.', CURRENT_TIMESTAMP, FALSE)");
            stmt.executeUpdate("INSERT INTO SOPORTE VALUES " +
                    "(4, 6, 'Problema con el pago', 'No se refleja el pago realizado con PayPal.', 'abierto', CURRENT_TIMESTAMP)");

            System.out.println("Datos de prueba insertados correctamente.");

        } catch (SQLException e) {
            System.err.println("Error al insertar datos de prueba:");
            e.printStackTrace();
        }
    }

}