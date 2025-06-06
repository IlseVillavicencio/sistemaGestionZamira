package sistemaventazamira.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sistemaventazamira.model.ConexionDB;
import sistemaventazamira.model.DetallePedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.util.StringConverter;
import sistemaventazamira.model.Producto;

public class PedidoController {

    @FXML private ComboBox<Producto> productoComboBox;
    @FXML private TextField cantidadProductoTextField;
    @FXML private TableView<CarritoItem> carritoTableView;
    @FXML private TableColumn<CarritoItem, String> productoColumn;
    @FXML private TableColumn<CarritoItem, Integer> cantidadColumn;
    @FXML private TableColumn<CarritoItem, Double> precioUnitarioColumn;
    @FXML private TableColumn<CarritoItem, Double> subtotalColumn;
    @FXML private TextField totalTextField;

    @FXML private TextField nombreUsuarioTextField;
    @FXML private TextField correoUsuarioTextField; // Nuevo campo para correo
    @FXML private TextField tarjetaPagoTextField;
    
    
    @FXML private ComboBox<String> estadoComboBox;
    @FXML private ComboBox<String> metodoEntregaComboBox;
    @FXML private ComboBox<String> metodoPagoComboBox;


    private ObservableList<Producto> listaProductos = FXCollections.observableArrayList();
    private ObservableList<CarritoItem> carritoItems = FXCollections.observableArrayList();

    public void initialize() {
        cargarProductosDesdeBD();
        
        // Cargar estados en estadoComboBox
    ObservableList<String> estados = FXCollections.observableArrayList(
        "Pendiente", "En Proceso", "Enviado", "Entregado", "Cancelado"
    );
    estadoComboBox.setItems(estados);
    estadoComboBox.getSelectionModel().selectFirst();

    // Cargar métodos de entrega
    ObservableList<String> metodosEntrega = FXCollections.observableArrayList(
        "Recojo en tienda", "Entrega a domicilio", "Envío urgente"
    );
    metodoEntregaComboBox.setItems(metodosEntrega);
    metodoEntregaComboBox.getSelectionModel().selectFirst();

    // Cargar métodos de pago
    ObservableList<String> metodosPago = FXCollections.observableArrayList(
        "Tarjeta de crédito", "Tarjeta de débito", "PayPal", "Efectivo"
    );
    metodoPagoComboBox.setItems(metodosPago);
    metodoPagoComboBox.getSelectionModel().selectFirst();


        productoComboBox.setConverter(new StringConverter<Producto>() {
            @Override
            public String toString(Producto producto) {
                return producto == null ? "" : producto.getNombre();
            }
            @Override
            public Producto fromString(String string) {
                return null;
            }
        });

        productoComboBox.setItems(listaProductos);

        productoColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getProducto()));
        cantidadColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getCantidad()).asObject());
        precioUnitarioColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getPrecioUnitario()).asObject());
        subtotalColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleDoubleProperty(data.getValue().getSubtotal()).asObject());

        carritoTableView.setItems(carritoItems);
    }

    private void cargarProductosDesdeBD() {
        listaProductos.clear();

        String sql = "SELECT id_producto, nombre, precio FROM PRODUCTO";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_producto");
                String nombre = rs.getString("nombre");
                double precio = rs.getDouble("precio");
                listaProductos.add(new Producto(id, nombre, precio));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta("Error al cargar productos desde la base de datos");
        }
    }

    @FXML
    private void agregarProductoAlCarrito() {
        Producto productoSeleccionado = productoComboBox.getValue();
        if (productoSeleccionado == null) {
            mostrarAlerta("Debes seleccionar un producto");
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(cantidadProductoTextField.getText());
            if (cantidad <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            mostrarAlerta("Cantidad inválida");
            return;
        }

        // Actualizar cantidad si producto ya está en el carrito
        for (CarritoItem item : carritoItems) {
            if (item.getIdProducto() == productoSeleccionado.getId()) {
                int nuevaCantidad = item.getCantidad() + cantidad;
                carritoItems.remove(item);
                carritoItems.add(new CarritoItem(productoSeleccionado.getId(), productoSeleccionado.getNombre(), nuevaCantidad, productoSeleccionado.getPrecio()));
                actualizarTotal();
                return;
            }
        }

        // Agregar nuevo producto al carrito
        CarritoItem nuevoItem = new CarritoItem(
            productoSeleccionado.getId(),
            productoSeleccionado.getNombre(),
            cantidad,
            productoSeleccionado.getPrecio()
        );

        carritoItems.add(nuevoItem);
        actualizarTotal();
    }

    private void actualizarTotal() {
        double total = carritoItems.stream().mapToDouble(CarritoItem::getSubtotal).sum();
        totalTextField.setText(String.format("%.2f", total));
    }

    @FXML
    private void realizarPedido() {
        String nombreUsuario = nombreUsuarioTextField.getText().trim();
        String correoUsuario = correoUsuarioTextField.getText().trim();
        //String tarjetaPago = tarjetaPagoTextField.getText().trim();

        if (nombreUsuario.isEmpty() || correoUsuario.isEmpty()) {
            mostrarAlerta("Debes completar los datos del usuario y el pago");
            return;
        }

        if (carritoItems.isEmpty()) {
            mostrarAlerta("El carrito está vacío");
            return;
        }

        Connection conn = null;
        PreparedStatement psUsuario = null;
        PreparedStatement psPedido = null;
        PreparedStatement psDetalle = null;
        PreparedStatement psPago = null;

        try {
            conn = ConexionDB.conectar();
            conn.setAutoCommit(false); // Inicio transacción

            // Insertar usuario
            String sqlUsuario = "INSERT INTO USUARIO (nombre, correo) VALUES (?, ?)";
            psUsuario = conn.prepareStatement(sqlUsuario, Statement.RETURN_GENERATED_KEYS);
            psUsuario.setString(1, nombreUsuario);
            psUsuario.setString(2, correoUsuario);
            psUsuario.executeUpdate();
            ResultSet rsUsuario = psUsuario.getGeneratedKeys();
            int idUsuario;
            if (rsUsuario.next()) {
                idUsuario = rsUsuario.getInt(1);
            } else {
                conn.rollback();
                mostrarAlerta("Error al insertar usuario");
                return;
            }

            // Insertar pedido
            String sqlPedido = "INSERT INTO PEDIDO (id_usuario, estado, total) VALUES (?, 'pendiente', ?)";
            psPedido = conn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
            psPedido.setInt(1, idUsuario);
            double total = carritoItems.stream().mapToDouble(CarritoItem::getSubtotal).sum();
            psPedido.setDouble(2, total);
            psPedido.executeUpdate();
            ResultSet rsPedido = psPedido.getGeneratedKeys();
            int idPedido;
            if (rsPedido.next()) {
                idPedido = rsPedido.getInt(1);
            } else {
                conn.rollback();
                mostrarAlerta("Error al insertar pedido");
                return;
            }

            // Insertar detalle_pedido
            String sqlDetalle = "INSERT INTO DETALLE_PEDIDO (id_pedido, id_producto, cantidad, precio_unitario, producto, subtotal) VALUES (?, ?, ?, ?, ?, ?)";
            psDetalle = conn.prepareStatement(sqlDetalle);
            for (CarritoItem item : carritoItems) {
                psDetalle.setInt(1, idPedido);
                psDetalle.setInt(2, item.getIdProducto());
                psDetalle.setInt(3, item.getCantidad());
                psDetalle.setDouble(4, item.getPrecioUnitario());
                psDetalle.setString(5, item.getProducto());
                psDetalle.setDouble(6, item.getSubtotal());
                psDetalle.addBatch();
            }
            psDetalle.executeBatch();

            // Insertar pago
            String metodoPago = "tarjeta"; // Puedes adaptar esto para seleccionar método
            String sqlPago = "INSERT INTO PAGO (id_pedido, metodo, monto, estado) VALUES (?, ?, ?, 'completado')";
            psPago = conn.prepareStatement(sqlPago);
            psPago.setInt(1, idPedido);
            psPago.setString(2, metodoPago);
            psPago.setDouble(3, total);
            psPago.executeUpdate();

            conn.commit();

            mostrarAlerta("Pedido realizado con éxito");

            limpiarFormulario();

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            mostrarAlerta("Error al procesar el pedido");
        } finally {
            try {
                if (psUsuario != null) psUsuario.close();
                if (psPedido != null) psPedido.close();
                if (psDetalle != null) psDetalle.close();
                if (psPago != null) psPago.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void limpiarFormulario() {
        nombreUsuarioTextField.clear();
        correoUsuarioTextField.clear();
        //tarjetaPagoTextField.clear();
        cantidadProductoTextField.clear();
        carritoItems.clear();
        totalTextField.clear();
        productoComboBox.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}