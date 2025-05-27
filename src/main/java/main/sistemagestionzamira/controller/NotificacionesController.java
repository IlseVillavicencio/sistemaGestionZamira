/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package main.sistemagestionzamira.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * FXML Controller class
 *
 * @author ilse_
 */
public class NotificacionesController implements Initializable {

    @FXML
    private ListView<String> listaNotificaciones;

    @FXML
    private Button btnMarcarLeido;

    @FXML
    private Button btnEliminarTodas;

    private final ObservableList<String> notificaciones = FXCollections.observableArrayList(
            "🔔 Pedido #1203 ha sido entregado.",
            "🛠 Soporte técnico solicitado por Juan Pérez.",
            "🧾 Nueva factura generada para Cliente XYZ."
    );
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listaNotificaciones.setItems(notificaciones);
        
        btnMarcarLeido.setOnAction(e -> marcarComoLeido());
        btnEliminarTodas.setOnAction(e -> eliminarTodas());
    }
    
    public void marcarComoLeido() {
        String seleccionada = listaNotificaciones.getSelectionModel().getSelectedItem();
        if (seleccionada != null && !seleccionada.startsWith("✅")) {
            int index = listaNotificaciones.getSelectionModel().getSelectedIndex();
            notificaciones.set(index, "✅ " + seleccionada);
        }
    }
    
    public void eliminarTodas() {
        notificaciones.clear();
    }
    
}
