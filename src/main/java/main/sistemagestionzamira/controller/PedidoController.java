package main.sistemagestionzamira.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;



public class PedidoController implements Initializable {
    
   // 🧍 Usuario
    @FXML private TextField busquedaUsuarioTextField;
    @FXML private ListView<String> resultadosUsuarioListView;

    // 📅 Pedido
    @FXML private DatePicker fechaPedidoDatePicker;
    @FXML private ComboBox<String> estadoComboBox;

    // 🔍 Productos
    @FXML private TextField busquedaProductoTextField;
    @FXML private ComboBox<String> productoComboBox;
    @FXML private TextField cantidadProductoTextField;
    @FXML private Label precioUnitarioLabel;
    @FXML private Button agregarProductoButton;
    @FXML private ListView<String> productosListView;
    @FXML private TextField totalTextField;

    // 🚚 Entrega
    @FXML private ComboBox<String> metodoEntregaComboBox;
    @FXML private TextField direccionTextField;
    @FXML private DatePicker fechaEntregaDatePicker;

    // 💳 Pago
    @FXML private ComboBox<String> metodoPagoComboBox;
    @FXML private TextField numeroCuentaTextField;

    // ✅ Finalizar
    @FXML private Button realizarPedidoButton;

    // Datos simulados
    private final Map<String, Double> productosDisponibles = new HashMap<>();
    private final ObservableList<String> carrito = FXCollections.observableArrayList();
    private double totalPedido = 0.0;

    @FXML
    public void initialize() {
        // Inicializar combos
        estadoComboBox.setItems(FXCollections.observableArrayList("Pendiente", "Procesado", "Enviado", "Entregado"));
        metodoEntregaComboBox.setItems(FXCollections.observableArrayList("Recoger en tienda", "Envío a domicilio"));
        metodoPagoComboBox.setItems(FXCollections.observableArrayList("Tarjeta", "Transferencia", "Efectivo"));

        // Simular productos y precios
        productosDisponibles.put("Pan", 2.50);
        productosDisponibles.put("Leche", 1.80);
        productosDisponibles.put("Queso", 4.00);
        productosDisponibles.put("Huevos", 3.20);
        productoComboBox.setItems(FXCollections.observableArrayList(productosDisponibles.keySet()));

        productosListView.setItems(carrito);
        totalTextField.setText("0.00");

        productoComboBox.setOnAction(e -> actualizarPrecioUnitario());
        agregarProductoButton.setOnAction(e -> agregarProductoAlCarrito());
        realizarPedidoButton.setOnAction(e -> realizarPedido());
    }

    private void actualizarPrecioUnitario() {
        String producto = productoComboBox.getValue();
        if (producto != null && productosDisponibles.containsKey(producto)) {
            double precio = productosDisponibles.get(producto);
            precioUnitarioLabel.setText(String.format("Precio Unitario: $%.2f", precio));
        }
    }

    private void agregarProductoAlCarrito() {
        String producto = productoComboBox.getValue();
        String cantidadStr = cantidadProductoTextField.getText();

        if (producto == null || cantidadStr.isEmpty()) {
            mostrarAlerta("Error", "Debe seleccionar un producto y especificar la cantidad.");
            return;
        }

        try {
            int cantidad = Integer.parseInt(cantidadStr);
            if (cantidad <= 0) throw new NumberFormatException();

            double precio = productosDisponibles.get(producto);
            double subtotal = precio * cantidad;

            carrito.add(producto + " x" + cantidad + " - $" + String.format("%.2f", subtotal));
            totalPedido += subtotal;
            totalTextField.setText(String.format("%.2f", totalPedido));

            // Limpiar campos
            cantidadProductoTextField.clear();
            productoComboBox.getSelectionModel().clearSelection();
            precioUnitarioLabel.setText("Precio Unitario: $");

        } catch (NumberFormatException e) {
            mostrarAlerta("Cantidad inválida", "La cantidad debe ser un número entero positivo.");
        }
    }

    private void realizarPedido() {
        // Validaciones básicas
        if (resultadosUsuarioListView.getSelectionModel().isEmpty()) {
            mostrarAlerta("Falta usuario", "Debe seleccionar un usuario.");
            return;
        }

        if (carrito.isEmpty()) {
            mostrarAlerta("Carrito vacío", "Debe agregar al menos un producto.");
            return;
        }

        if (fechaPedidoDatePicker.getValue() == null) {
            mostrarAlerta("Falta fecha", "Debe seleccionar la fecha del pedido.");
            return;
        }

        // Simulación de creación de pedido
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Pedido realizado");
        alerta.setHeaderText(null);
        alerta.setContentText("El pedido ha sido registrado correctamente.");
        alerta.showAndWait();

        limpiarFormulario();
    }

    private void limpiarFormulario() {
        resultadosUsuarioListView.getItems().clear();
        fechaPedidoDatePicker.setValue(LocalDate.now());
        estadoComboBox.getSelectionModel().clearSelection();
        productoComboBox.getSelectionModel().clearSelection();
        cantidadProductoTextField.clear();
        precioUnitarioLabel.setText("Precio Unitario: $");
        carrito.clear();
        totalPedido = 0.0;
        totalTextField.setText("0.00");
        metodoEntregaComboBox.getSelectionModel().clearSelection();
        direccionTextField.clear();
        fechaEntregaDatePicker.setValue(null);
        metodoPagoComboBox.getSelectionModel().clearSelection();
        numeroCuentaTextField.clear();
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    // Método de búsqueda simulado (puedes mejorarlo con una base de datos)
    @FXML
    private void buscarUsuario(KeyEvent event) {
        String texto = busquedaUsuarioTextField.getText().toLowerCase();
        resultadosUsuarioListView.getItems().clear();

        if (!texto.isEmpty()) {
            if ("juan".contains(texto)) resultadosUsuarioListView.getItems().add("Juan Pérez");
            if ("maria".contains(texto)) resultadosUsuarioListView.getItems().add("María López");
            if ("carlos".contains(texto)) resultadosUsuarioListView.getItems().add("Carlos Ruiz");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializar combos
        estadoComboBox.setItems(FXCollections.observableArrayList("Pendiente", "Procesado", "Enviado", "Entregado"));
        metodoEntregaComboBox.setItems(FXCollections.observableArrayList("Recoger en tienda", "Envío a domicilio"));
        metodoPagoComboBox.setItems(FXCollections.observableArrayList("Tarjeta", "Transferencia", "Efectivo"));

        // Simular productos y precios
        productosDisponibles.put("Pan", 2.50);
        productosDisponibles.put("Leche", 1.80);
        productosDisponibles.put("Queso", 4.00);
        productosDisponibles.put("Huevos", 3.20);
        productoComboBox.setItems(FXCollections.observableArrayList(productosDisponibles.keySet()));

        productosListView.setItems(carrito);
        totalTextField.setText("0.00");

        productoComboBox.setOnAction(e -> actualizarPrecioUnitario());
        agregarProductoButton.setOnAction(e -> agregarProductoAlCarrito());
        realizarPedidoButton.setOnAction(e -> realizarPedido());
    }
}
