/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package main.sistemagestionzamira.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

/**
 * FXML Controller class
 *
 * @author ilse_
 */
public class InventarioController implements Initializable {
    @FXML
    private TableView<ProductoStock> inventarioTableView;

    @FXML
    private TableColumn<ProductoStock, String> productoColumn;

    @FXML
    private TableColumn<ProductoStock, Integer> stockColumn;

    private Connection connection;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inventarioTableView.setEditable(true);

        productoColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProductoProperty());

        stockColumn.setCellValueFactory(cellData -> cellData.getValue().stockActualProperty().asObject());

        stockColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        stockColumn.setOnEditCommit(event -> {
            ProductoStock producto = event.getRowValue();
            int stockAnterior = producto.getStockActual();
            int stockNuevo = event.getNewValue();

            if (stockNuevo != stockAnterior) {
                int diferencia = stockNuevo - stockAnterior;
                String tipoMovimiento = diferencia > 0 ? "entrada" : "salida";
                int cantidadMovimiento = Math.abs(diferencia);

                insertarMovimiento(producto.getIdProducto(), tipoMovimiento, cantidadMovimiento);

                producto.setStockActual(stockNuevo);
            }
        });
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
        cargarDatos();
    }

    private void cargarDatos() {
        if (connection == null) {
            System.err.println("La conexión a la base de datos no ha sido inicializada.");
            return;
        }

        ObservableList<ProductoStock> productos = FXCollections.observableArrayList();

        String sql = "SELECT p.id_producto, p.nombre_producto, " +
                "COALESCE(SUM(CASE WHEN i.tipo_movimiento = 'entrada' THEN i.cantidad ELSE 0 END),0) - " +
                "COALESCE(SUM(CASE WHEN i.tipo_movimiento = 'salida' THEN i.cantidad ELSE 0 END),0) AS stock_actual " +
                "FROM producto p " +
                "LEFT JOIN inventario i ON p.id_producto = i.id_producto " +
                "GROUP BY p.id_producto, p.nombre_producto";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id_producto");
                String nombre = rs.getString("nombre_producto");
                int stock = rs.getInt("stock_actual");

                productos.add(new ProductoStock(id, nombre, stock));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        inventarioTableView.setItems(productos);
    }

    private void insertarMovimiento(int idProducto, String tipoMovimiento, int cantidad) {
        if (connection == null) {
            System.err.println("No hay conexión a la base de datos para insertar movimiento.");
            return;
        }

        String sql = "INSERT INTO inventario (id_producto, tipo_movimiento, cantidad, fecha) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProducto);
            stmt.setString(2, tipoMovimiento);
            stmt.setInt(3, cantidad);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static class ProductoStock {
        private final IntegerProperty idProducto;
        private final StringProperty nombreProducto;
        private final IntegerProperty stockActual;

        public ProductoStock(int idProducto, String nombreProducto, int stockActual) {
            this.idProducto = new SimpleIntegerProperty(idProducto);
            this.nombreProducto = new SimpleStringProperty(nombreProducto);
            this.stockActual = new SimpleIntegerProperty(stockActual);
        }

        public int getIdProducto() {
            return idProducto.get();
        }

        public String getNombreProducto() {
            return nombreProducto.get();
        }

        public int getStockActual() {
            return stockActual.get();
        }

        public void setStockActual(int stockActual) {
            this.stockActual.set(stockActual);
        }

        public IntegerProperty stockActualProperty() {
            return stockActual;
        }

        public StringProperty nombreProductoProperty() {
            return nombreProducto;
        }
    }
}
