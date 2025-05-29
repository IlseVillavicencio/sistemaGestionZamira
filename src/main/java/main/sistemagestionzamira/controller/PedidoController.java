package main.sistemagestionzamira.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.HashMap;
import java.util.Map;

public class PedidoController {

    @FXML
    private ComboBox<String> productoComboBox;
    @FXML
    private TextField cantidadProductoTextField;
    @FXML
    private Label precioUnitarioLabel;
    @FXML
    private Button agregarProductoButton;
    @FXML
    private TableView<CarritoItem> carritoTableView;
    @FXML
    private TableColumn<CarritoItem, String> productoColumn;
    @FXML
    private TableColumn<CarritoItem, Integer> cantidadColumn;
    @FXML
    private TableColumn<CarritoItem, Double> precioUnitarioColumn;
    @FXML
    private TableColumn<CarritoItem, Double> subtotalColumn;
    @FXML
    private TextField totalTextField;

    // Simulación de productos: nombre -> precio
    private Map<String, Double> productosSimulados = new HashMap<>();

    // Lista observable para carrito
    private ObservableList<CarritoItem> carritoItems = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Cargar productos simulados
        productosSimulados.put("Piñata de Estrella", 250.0);
        productosSimulados.put("Piñata de Bluey", 40.0);
        productosSimulados.put("Piñata Spiderman", 60.0);
        productosSimulados.put("Piñata del numero 1", 15.0);
        productosSimulados.put("Piñata de Stitch", 100.0);

        // Poner productos en ComboBox
        productoComboBox.setItems(FXCollections.observableArrayList(productosSimulados.keySet()));

        // Configurar tabla
        productoColumn.setCellValueFactory(new PropertyValueFactory<>("producto"));
        cantidadColumn.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        precioUnitarioColumn.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
        subtotalColumn.setCellValueFactory(new PropertyValueFactory<>("subtotal"));

        carritoTableView.setItems(carritoItems);

        // Mostrar precio unitario cuando cambie el producto seleccionado
        productoComboBox.setOnAction(e -> {
            String seleccionado = productoComboBox.getValue();
            if (seleccionado != null && productosSimulados.containsKey(seleccionado)) {
                double precio = productosSimulados.get(seleccionado);
                precioUnitarioLabel.setText("Precio Unitario: $" + String.format("%.2f", precio));
            } else {
                precioUnitarioLabel.setText("Precio Unitario: $");
            }
        });

        // Botón agregar producto
        agregarProductoButton.setOnAction(e -> agregarProductoAlCarrito());
    }

    private void agregarProductoAlCarrito() {
        String producto = productoComboBox.getValue();
        String cantidadStr = cantidadProductoTextField.getText();

        if (producto == null || producto.isEmpty()) {
            mostrarAlerta("Debe seleccionar un producto.");
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(cantidadStr);
            if (cantidad <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            mostrarAlerta("Cantidad inválida. Debe ser un número entero positivo.");
            return;
        }

        double precioUnitario = productosSimulados.get(producto);
        double subtotal = precioUnitario * cantidad;

        // Añadir al carrito
        carritoItems.add(new CarritoItem(producto, cantidad, precioUnitario, subtotal));

        // Actualizar total
        actualizarTotal();

        // Limpiar campos
        cantidadProductoTextField.clear();
        productoComboBox.getSelectionModel().clearSelection();
        precioUnitarioLabel.setText("Precio Unitario: $");
    }

    private void actualizarTotal() {
        double total = 0;
        for (CarritoItem item : carritoItems) {
            total += item.getSubtotal();
        }
        totalTextField.setText(String.format("%.2f", total));
    }

    private void mostrarAlerta(String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    // Clase interna para items del carrito
    public static class CarritoItem {
        private final String producto;
        private final int cantidad;
        private final double precioUnitario;
        private final double subtotal;

        public CarritoItem(String producto, int cantidad, double precioUnitario, double subtotal) {
            this.producto = producto;
            this.cantidad = cantidad;
            this.precioUnitario = precioUnitario;
            this.subtotal = subtotal;
        }

        public String getProducto() {
            return producto;
        }

        public int getCantidad() {
            return cantidad;
        }

        public double getPrecioUnitario() {
            return precioUnitario;
        }

        public double getSubtotal() {
            return subtotal;
        }
    }
}


