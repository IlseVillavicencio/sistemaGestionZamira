package main.sistemagestionzamira;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.sql.Statement;
//import main.sistemagestionzamira.model.ConexionDB;

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

        // Tamaño y posición ventana centrada
        double width = 800;
        double height = 600;
        stage.setWidth(width);
        stage.setHeight(height);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);

        stage.show();

        // Insertar datos de prueba después de mostrar la ventana
        //try {
            //insertarDatosPrueba();
        //} catch (Exception e) {
            //System.err.println("Error al insertar datos de prueba:");
            //e.printStackTrace();
        //}
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

    //public void insertarDatosPrueba() {
    //try (Connection conn = ConexionDB.conectar();
         //Statement stmt = conn.createStatement()) {

        // Insertar usuarios (sin id_usuario porque es auto_increment)
        //stmt.executeUpdate("INSERT INTO USUARIO (nombre, correo) VALUES " +
                //"('Carlos Ramírez', 'carlos.ramirez@mail.com'), " +
                //"('Lucía Martínez', 'lucia.martinez@mail.com')");

        // Insertar productos
        //stmt.executeUpdate("INSERT INTO PRODUCTO (nombre, precio, descripcion, stock) VALUES " +
                //"('Piñata Spiderman', 299.99, 'Piñata de Spiderman', 10), " +
                //"('Piñata de estrella', 255.50, 'Piñata de estrella', 50)");

        // Para insertar PEDIDO, primero necesitamos el id_usuario real
        // Supongamos que el id_usuario de 'Carlos Ramírez' es 1 (puede que tengas que consultar)
        // Para pruebas rápidas puedes usar LAST_INSERT_ID() o hacer consultas previas,
        // aquí pongo directamente 1, pero ideal es obtenerlo tras insertar.
        //int idUsuario = 1;  // AJUSTAR según datos reales o consultarlo antes

        //stmt.executeUpdate("INSERT INTO PEDIDO (id_usuario, fecha, estado, total) VALUES " +
                //"(" + idUsuario + ", CURRENT_TIMESTAMP, 'procesando', 325.49)");

        // Lo mismo para id_pedido, supongamos que es 1 (ajustar en ambiente real)
        //int idPedido = 1;

        // Insertar detalles del pedido
        // Supongamos que los productos insertados tienen id_producto 1 y 2 (ajustar si no es así)
        //int idProducto1 = 1;
        //int idProducto2 = 2;

        //stmt.executeUpdate("INSERT INTO DETALLE_PEDIDO (id_pedido, id_producto, cantidad, precio_unitario) VALUES " +
                //"(" + idPedido + ", " + idProducto1 + ", 1, 299.99), " +
                //"(" + idPedido + ", " + idProducto2 + ", 1, 255.50)");

        // Insertar pago
        //stmt.executeUpdate("INSERT INTO PAGO (id_pedido, metodo, monto, estado) VALUES " +
                //"(" + idPedido + ", 'paypal', 555.49, 'pendiente')");

        // Insertar movimientos en inventario (salida de productos vendidos)
        //stmt.executeUpdate("INSERT INTO INVENTARIO (id_producto, tipo_movimiento, cantidad, fecha) VALUES " +
                //"(" + idProducto1 + ", 'salida', 1, CURRENT_TIMESTAMP), " +
                //"(" + idProducto2 + ", 'salida', 1, CURRENT_TIMESTAMP)");

        // Insertar envío
        //stmt.executeUpdate("INSERT INTO ENVIO (id_pedido, metodo_entrega, direccion_entrega, estado, fecha_estimada) VALUES " +
                //"(" + idPedido + ", 'Entrega estándar', 'Av. Libertad 456', 'preparando', DATE_ADD(CURRENT_DATE, INTERVAL 5 DAY))");

        // Insertar notificación para usuario
        //stmt.executeUpdate("INSERT INTO NOTIFICACION (id_usuario, mensaje, fecha, leida) VALUES " +
                //"(" + idUsuario + ", 'Tu pedido será enviado pronto.', CURRENT_TIMESTAMP, FALSE)");

        // Insertar ticket de soporte
        // Supongamos que 'Lucía Martínez' tiene id_usuario = 2
        //int idUsuario2 = 2;
        //stmt.executeUpdate("INSERT INTO SOPORTE (id_usuario, asunto, descripcion, estado, fecha_creacion) VALUES " +
                //"(" + idUsuario2 + ", 'Problema con el pago', 'No se refleja el pago realizado con PayPal.', 'abierto', CURRENT_TIMESTAMP)");

        //System.out.println("Datos de prueba insertados correctamente.");

    //} catch (SQLException e) {
        //System.err.println("Error al insertar datos de prueba:");
        //e.printStackTrace();
    //}
//}

}
