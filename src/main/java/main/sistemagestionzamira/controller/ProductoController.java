/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package main.sistemagestionzamira.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import main.sistemagestionzamira.model.ConexionDB;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * FXML Controller class
 *
 * @author ilse_
 */
public class ProductoController implements Initializable {
    
    @FXML
    private TextField txtNombreProducto;
    @FXML
    private TextField txtPrecioProducto;
    @FXML
    private TextField txtStockProducto;

    @FXML
    public void guardarProducto() {
        String nombre = txtNombreProducto.getText();
        double precio;
        int stock;

        try {
            precio = Double.parseDouble(txtPrecioProducto.getText());
            stock = Integer.parseInt(txtStockProducto.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta("Entrada inválida", "Precio o stock no son válidos.", AlertType.ERROR);
            return;
        }

        String sql = "INSERT INTO productos(nombre, precio, stock) VALUES (?, ?, ?)";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setDouble(2, precio);
            stmt.setInt(3, stock);
            stmt.executeUpdate();

            mostrarAlerta("Éxito", "Producto guardado exitosamente.", AlertType.INFORMATION);

            txtNombreProducto.clear();
            txtPrecioProducto.clear();
            txtStockProducto.clear();

        } catch (SQLException e) {
            mostrarAlerta("Error de base de datos", "Error al guardar producto: " + e.getMessage(), AlertType.ERROR);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicialización si es necesaria
    }
    
}
