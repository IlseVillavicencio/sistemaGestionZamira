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
public class InventarioDAO {
    public List<Inventario> obtenerMovimientos() {
        List<Inventario> lista = new ArrayList<>();
        String sql = "SELECT * FROM INVENTARIO";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Inventario(
                    rs.getInt("id_movimiento"),
                    rs.getInt("id_producto"),
                    rs.getString("tipo_movimiento"),
                    rs.getInt("cantidad"),
                    rs.getTimestamp("fecha").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void insertarMovimiento(Inventario mov) {
        String sql = "INSERT INTO INVENTARIO (id_movimiento, id_producto, tipo_movimiento, cantidad, fecha) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, mov.getIdMovimiento());
            stmt.setInt(2, mov.getIdProducto());
            stmt.setString(3, mov.getTipoMovimiento());
            stmt.setInt(4, mov.getCantidad());
            stmt.setTimestamp(5, Timestamp.valueOf(mov.getFecha()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
