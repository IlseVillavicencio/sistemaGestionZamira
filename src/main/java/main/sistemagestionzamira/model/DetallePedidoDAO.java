/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.sistemagestionzamira.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ilse_
 */
public class DetallePedidoDAO {
    public List<DetallePedido> obtenerDetalles() {
        List<DetallePedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM DETALLE_PEDIDO";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new DetallePedido(
                    rs.getInt("id_detalle"),
                    rs.getInt("id_pedido"),
                    rs.getInt("id_producto"),
                    rs.getInt("cantidad"),
                    rs.getDouble("precio_unitario")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void insertarDetalle(DetallePedido detalle) {
        String sql = "INSERT INTO DETALLE_PEDIDO (id_detalle, id_pedido, id_producto, cantidad, precio_unitario) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, detalle.getIdDetalle());
            stmt.setInt(2, detalle.getIdPedido());
            stmt.setInt(3, detalle.getIdProducto());
            stmt.setInt(4, detalle.getCantidad());
            stmt.setDouble(5, detalle.getPrecioUnitario());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
