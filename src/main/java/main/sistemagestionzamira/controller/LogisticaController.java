/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package main.sistemagestionzamira.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author ilse_
 */
public class LogisticaController implements Initializable {

    @FXML
    private ListView<String> listaEnvios;

    @FXML
    private TextField campoPedido;

    @FXML
    private TextField campoDireccion;

    @FXML
    private Button btnProgramar;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
    }
    
    @FXML
    private void programarEnvio() {
        String pedido = campoPedido.getText();
        String direccion = campoDireccion.getText();
        if (!pedido.isEmpty() && !direccion.isEmpty()) {
            listaEnvios.getItems().add("🚚 Pedido " + pedido + " a " + direccion);
            campoPedido.clear();
            campoDireccion.clear();
        }
    }
    
}
