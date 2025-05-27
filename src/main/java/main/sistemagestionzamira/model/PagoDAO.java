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
public class PagoDAO {
    public List<Pago> obtenerPagos() {
        List<Pago> lista = new ArrayList<>();
        String sql = "SELECT * FROM PAGO";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Pago(
                    rs.getInt("id_pago"),
                    rs.getInt("id_pedido"),
                    rs.getDouble("monto"),
                    rs.getString("metodo"),
                    rs.getString("estado")    
                    //rs.getDate("fecha_pago").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void insertarPago(Pago pago) {
        String sql = "INSERT INTO PAGO (id_pago, id_pedido, metodo, monto, estado) VALUES (?, ?, ?, ?, ?)";
    try (Connection conn = ConexionDB.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        System.out.println("Insertando pago: " + pago.getIdPago() + ", " + pago.getIdPedido() + ", "
                + pago.getMetodo() + ", " + pago.getMonto() + ", " + pago.getEstado());

        stmt.setInt(1, pago.getIdPago());
        stmt.setInt(2, pago.getIdPedido());
        stmt.setString(3, pago.getMetodo());
        stmt.setDouble(4, pago.getMonto());
        stmt.setString(5, pago.getEstado());

        int filasAfectadas = stmt.executeUpdate();
        System.out.println("Filas afectadas: " + filasAfectadas);

    } catch (SQLException e) {
        System.err.println("Error al insertar pago");
        e.printStackTrace();
    }
  }
}
