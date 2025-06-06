/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemaventazamira.controller;


import javafx.fxml.FXMLLoader;



import java.io.IOException;
import java.net.URL;
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
        cargarVista("/src/fxml/pedido.fxml");
    }

    @FXML
    public void irAFacturacion() {
        cargarVista("/src/fxml/facturacion.fxml");
    }

    @FXML
    public void irANotificaciones() {
        cargarVista("/src/fxml/notificaciones.fxml");
    }

    @FXML
    public void irASoporte() {
        cargarVista("/src/fxml/soporte.fxml");
    }

    @FXML
    public void irALogistica() {
        cargarVista("/src/fxml/logistica.fxml");
    }

    @FXML
    public void irAAdmin() {
        cargarVista("/src/fxml/admin.fxml");
        
    }
    
    @FXML 
    public void irAUsuario() {
        cargarVista("/src/fxml/usuario.fxml");
    }

    @FXML 
    public void irAProducto() {
        cargarVista("/src/fxml/producto.fxml");
    }
    
     @FXML 
    public void irAInventario() {
        cargarVista("/src/fxml/inventario.fxml");
    }

    public void cargarVista(String rutaFXML) {
        try {
            URL url = getClass().getResource(rutaFXML);
            if (url == null) {
                System.err.println("No se encontró el recurso: " + rutaFXML);
                return;
            }
            FXMLLoader loader = new FXMLLoader(url);
            Node vista = loader.load();
            contenidoScrollPane.setContent(vista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
