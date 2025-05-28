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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import main.sistemagestionzamira.model.ConexionDB;

/**
 * FXML Controller class
 *
 * @author ilse_
 */
public class UsuarioController implements Initializable {

    @FXML
    private TextField txtNombreUsuario;
    @FXML
    private TextField txtCorreoUsuario;
    @FXML
    private TextField txtPasswordUsuario;

    @FXML
    public void guardarUsuario(ActionEvent event) {
        String nombre = txtNombreUsuario.getText();
        String correo = txtCorreoUsuario.getText();
        String password = txtPasswordUsuario.getText();

        if (nombre.isEmpty() || correo.isEmpty() || password.isEmpty()) {
            mostrarAlerta("Campos vacíos", "Por favor, llena todos los campos.", AlertType.ERROR);
            return;
        }

        // Aquí podrías agregar validación más avanzada para correo y password si quieres

        String sql = "INSERT INTO usuarios(nombre, correo, password) VALUES (?, ?, ?)";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setString(2, correo);
            stmt.setString(3, password);
            stmt.executeUpdate();

            mostrarAlerta("Éxito", "Usuario guardado exitosamente.", AlertType.INFORMATION);

            txtNombreUsuario.clear();
            txtCorreoUsuario.clear();
            txtPasswordUsuario.clear();

        } catch (SQLException e) {
            mostrarAlerta("Error de base de datos", "Error al guardar usuario: " + e.getMessage(), AlertType.ERROR);
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
