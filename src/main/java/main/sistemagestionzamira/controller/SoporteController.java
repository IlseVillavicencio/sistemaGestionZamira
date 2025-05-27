/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.sistemagestionzamira.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author ilse_
 */
public class SoporteController implements Initializable {
     @FXML
    private ListView<String> listaTickets;

    @FXML
    private TextField campoAsunto;

    @FXML
    private TextArea campoDescripcion;

    @FXML
    private Button btnEnviar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Inicialización si es necesaria
    }

    @FXML
    private void enviarTicket() {
        String asunto = campoAsunto.getText();
        String descripcion = campoDescripcion.getText();
        if (!asunto.isEmpty() && !descripcion.isEmpty()) {
            listaTickets.getItems().add("🛠️ " + asunto + ": " + descripcion);
            campoAsunto.clear();
            campoDescripcion.clear();
        }
    }
    
}
