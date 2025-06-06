/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package sistemaventazamira.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;
import sistemaventazamira.model.Factura;
/**
 * FXML Controller class
 *
 * @author ilse_
 */
public class FacturacionController implements Initializable {

    @FXML
    private TableView<Factura> tablaFacturas;

    @FXML
    private Button btnNuevaFactura;

    @FXML
    private Button btnVerDetalles;

    @FXML
    private Button btnEliminar;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        configurarTabla();
    }

    private void configurarTabla() {
        TableColumn<Factura, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Factura, String> colCliente = new TableColumn<>("Cliente");
        colCliente.setCellValueFactory(new PropertyValueFactory<>("cliente"));

        TableColumn<Factura, Double> colTotal = new TableColumn<>("Total");
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        tablaFacturas.getColumns().addAll(colId, colCliente, colTotal);
        
        btnNuevaFactura.setOnAction(e -> nuevaFactura());
        btnVerDetalles.setOnAction(e -> verDetalles());
        btnEliminar.setOnAction(e -> eliminarFactura());
        
    }

    // Métodos de ejemplo (puedes conectar estos a los botones con fx:id)
    public void nuevaFactura() {
        System.out.println("Crear nueva factura...");
    }

    public void verDetalles() {
        System.out.println("Ver detalles de factura seleccionada...");
    }

    public void eliminarFactura() {
        System.out.println("Eliminar factura seleccionada...");
    }
    
}
