/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaventazamira.model;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import sistemaventazamira.controller.CarritoItem;

/**
 *
 * @author ilse_
 */
public class PedidoDAO {

    public static void guardarPedido(int idUsuario, String estado, List<CarritoItem> productos) throws SQLException {
        Connection conn = null;
        PreparedStatement psPedido = null;
        PreparedStatement psDetalle = null;
        ResultSet rsKeys = null;


        try {
            conn = ConexionDB.conectar();
            conn.setAutoCommit(false); // Para manejar transacción
       
 
           // Calcular total
            double total = productos.stream()
                                   .mapToDouble(item -> item.getCantidad() * item.getPrecioUnitario())
                                   .sum();

            // Insertar pedido
            String sqlPedido = "INSERT INTO PEDIDO (id_usuario, estado, total) VALUES (?, ?, ?)";
            psPedido = conn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS);
            psPedido.setInt(1, idUsuario);
            psPedido.setString(2, estado);
            psPedido.setDouble(3, total);
            psPedido.executeUpdate();

            rsKeys = psPedido.getGeneratedKeys();
            if (!rsKeys.next()) {
                throw new SQLException("No se pudo obtener el id del pedido insertado.");
            }
            int idPedido = rsKeys.getInt(1);

            // Insertar detalles
            String sqlDetalle = "INSERT INTO DETALLE_PEDIDO (id_pedido, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";
            psDetalle = conn.prepareStatement(sqlDetalle);

            for (CarritoItem item : productos) {
                psDetalle.setInt(1, idPedido);
                psDetalle.setInt(2, item.getIdProducto());  // Asegúrate de tener este dato
                psDetalle.setInt(3, item.getCantidad());
                psDetalle.setDouble(4, item.getPrecioUnitario());
                psDetalle.addBatch();
            }
            psDetalle.executeBatch();

            // Al confirmar pedido, registrar salida en INVENTARIO para descontar stock
            String sqlInventario = "INSERT INTO INVENTARIO (id_producto, tipo_movimiento, cantidad) VALUES (?, 'salida', ?)";
            try (PreparedStatement psInv = conn.prepareStatement(sqlInventario)) {
                for (CarritoItem item : productos) {
                    psInv.setInt(1, item.getIdProducto());
                    psInv.setInt(2, item.getCantidad());
                    psInv.addBatch();
                }
                psInv.executeBatch();
            }

            conn.commit();

        } catch (SQLException e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (rsKeys != null) rsKeys.close();
            if (psPedido != null) psPedido.close();
            if (psDetalle != null) psDetalle.close();
            if (conn != null) conn.close();
        }
    }
}
