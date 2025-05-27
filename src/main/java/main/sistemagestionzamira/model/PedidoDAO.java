/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.sistemagestionzamira.model;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ilse_
 */
public class PedidoDAO {
     public List<Pedido> obtenerPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM PEDIDO";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                pedidos.add(new Pedido(
                    rs.getInt("id_pedido"),
                    rs.getInt("id_usuario"),
                    rs.getTimestamp("fecha").toLocalDateTime(),
                    rs.getString("estado"),
                    rs.getDouble("total")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pedidos;
    }

    public void insertarPedido(Pedido pedido) {
        String sql = "INSERT INTO PEDIDO (id_pedido, id_usuario, fecha, estado, total) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pedido.getIdPedido());
            stmt.setInt(2, pedido.getIdUsuario());
            stmt.setTimestamp(3, Timestamp.valueOf(pedido.getFecha()));
            stmt.setString(4, pedido.getEstado());
            stmt.setDouble(5, pedido.getTotal());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
