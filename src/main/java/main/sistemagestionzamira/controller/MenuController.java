/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package main.sistemagestionzamira.controller;


import javafx.fxml.FXMLLoader;



import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;

/**
 * FXML Controller class
 *
 * @author ilse_
 */
public class MenuController {

    @FXML
    public ScrollPane contenidoScrollPane;

    @FXML
    public void initialize() {
        // Cargar la vista de inicio al iniciar la aplicación
        cargarVista("/fxml/pedido.fxml");
    }

    

    @FXML
    public void irAPedido() {
        cargarVista("/fxml/pedido.fxml");
    }

    @FXML
    public void irAFacturacion() {
        cargarVista("/fxml/facturacion.fxml");
    }

    @FXML
    public void irANotificaciones() {
        cargarVista("/fxml/notificaciones.fxml");
    }

    @FXML
    public void irASoporte() {
        cargarVista("/fxml/soporte.fxml");
    }

    @FXML
    public void irALogistica() {
        cargarVista("/fxml/logistica.fxml");
    }

    @FXML
    public void irAAdmin() {
        cargarVista("/fxml/admin.fxml");
    }

    public void cargarVista(String rutaFXML) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Node vista = loader.load();
            contenidoScrollPane.setContent(vista);
        } catch (IOException e) {
            
            // Puedes mostrar una alerta al usuario si ocurre un error
        }
    }
}
