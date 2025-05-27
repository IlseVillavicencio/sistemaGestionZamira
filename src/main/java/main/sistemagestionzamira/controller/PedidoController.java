package main.sistemagestionzamira.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.sistemagestionzamira.model.DatosDePrueba;


public class PedidoController implements Initializable {

    @FXML
    private ComboBox<String> productoComboBox;

    @FXML
    private Button agregarProductoButton;

    @FXML
    private ListView<String> productosListView;
    
    @FXML
    private Button btnCargarDatos;


    //@FXML
    //private ListView<String> listaCarrito;

    @FXML
    private ComboBox<String> metodoPagoComboBox;

    @FXML
    private TextField numeroCuentaTextField;

    @FXML
    private ComboBox<String> metodoEntregaComboBox;

    @FXML
    private TextField direccionTextField;

    @FXML
    private DatePicker fechaEntregaDatePicker;

    @FXML
    private Button realizarPedidoButton;

    private final ObservableList<String> productosDisponibles = FXCollections.observableArrayList(
            "Piñata Unicornio", "Piñata Spiderman", "Piñata Dinosaurio"
    );

    private final ObservableList<String> carrito = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Inicializar combo productos
        productoComboBox.setItems(productosDisponibles);
        productoComboBox.setPromptText("Selecciona producto");

        // Inicializar métodos de pago
        metodoPagoComboBox.getItems().addAll("Tarjeta de crédito", "Tarjeta de débito", "PayPal");
        metodoPagoComboBox.setPromptText("Selecciona método de pago");

        // Inicializar métodos de entrega
        metodoEntregaComboBox.getItems().addAll("Envío a domicilio", "Recoger en tienda");
        metodoEntregaComboBox.setPromptText("Selecciona entrega");

        // Asociar botón agregar producto
        agregarProductoButton.setOnAction(e -> agregarProducto());

        // Asociar botón realizar pedido
        realizarPedidoButton.setOnAction(e -> realizarPago());

        // Inicializar listas
        //productosListView.setItems(productosDisponibles);
        productosListView.setItems(carrito);

        // Limpiar campos al inicio
        numeroCuentaTextField.clear();
        direccionTextField.clear();
        fechaEntregaDatePicker.setValue(null);
    }

    private void agregarProducto() {
        String producto = productoComboBox.getValue();
        if (producto == null || producto.isEmpty()) {
            mostrarAlerta("Error", "Selecciona un producto para agregar");
            return;
        }
        if (!carrito.contains(producto)) {
            carrito.add(producto);
            mostrarAlerta("Info", producto + " agregado al carrito");
        } else {
            mostrarAlerta("Info", "El producto ya está en el carrito");
        }
    }

    private void realizarPago() {
        String metodoPago = metodoPagoComboBox.getValue();
        String metodoEntrega = metodoEntregaComboBox.getValue();
        String direccion = direccionTextField.getText();
        String numeroCuenta = numeroCuentaTextField.getText();

        if (carrito.isEmpty()) {
            mostrarAlerta("Error", "No hay productos en el carrito");
            return;
        }

        if (metodoPago == null || metodoPago.isEmpty()) {
            mostrarAlerta("Error", "Selecciona un método de pago");
            return;
        }
        if (numeroCuenta == null || numeroCuenta.trim().isEmpty()) {
            mostrarAlerta("Error", "Ingresa el número de tarjeta o cuenta");
            return;
        }
        if (metodoEntrega == null || metodoEntrega.isEmpty()) {
            mostrarAlerta("Error", "Selecciona un método de entrega");
            return;
        }
        if (metodoEntrega.equals("Envío a domicilio")) {
            if (direccion == null || direccion.trim().isEmpty()) {
                mostrarAlerta("Error", "Debes proporcionar una dirección para el envío a domicilio");
                return;
            }
            if (fechaEntregaDatePicker.getValue() == null) {
                mostrarAlerta("Error", "Selecciona una fecha de entrega");
                return;
            }
        }

        // --- Lógica simulada para procesar el pago y la orden ---
        boolean pagoExitoso = procesarPago(metodoPago, numeroCuenta);
        if (!pagoExitoso) {
            mostrarAlerta("Error", "El pago no pudo ser procesado. Intenta de nuevo.");
            return;
        }

        boolean ordenProcesada = procesarOrden(carrito, metodoEntrega, direccion, fechaEntregaDatePicker.getValue());
        if (!ordenProcesada) {
            mostrarAlerta("Error", "No se pudo procesar la orden. Intenta de nuevo.");
            return;
        }

        mostrarAlerta("Éxito", "Pago y orden realizados correctamente");

        // Limpiar todo después de pagar
        carrito.clear();
        productoComboBox.getSelectionModel().clearSelection();
        metodoPagoComboBox.getSelectionModel().clearSelection();
        metodoEntregaComboBox.getSelectionModel().clearSelection();
        numeroCuentaTextField.clear();
        direccionTextField.clear();
        fechaEntregaDatePicker.setValue(null);
    }

    // Simula el procesamiento del pago
    private boolean procesarPago(String metodoPago, String numeroCuenta) {
        // Aquí pondrías la integración real con un sistema de pago
        System.out.println("Procesando pago con " + metodoPago + ", cuenta: " + numeroCuenta);
        // Simulamos que siempre es exitoso
        return true;
    }

    // Simula el procesamiento de la orden
    private boolean procesarOrden(ObservableList<String> productos, String metodoEntrega, String direccion, java.time.LocalDate fechaEntrega) {
        System.out.println("Procesando orden:");
        System.out.println("Productos: " + productos);
        System.out.println("Método de entrega: " + metodoEntrega);
        if (metodoEntrega.equals("Envío a domicilio")) {
            System.out.println("Dirección: " + direccion);
            System.out.println("Fecha entrega: " + fechaEntrega);
        }
        // Simulamos que siempre es exitoso
        return true;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    @FXML
    private void cargarDatosDePrueba() {
    DatosDePrueba datos = new DatosDePrueba();
    datos.insertarDatos();
}

}
