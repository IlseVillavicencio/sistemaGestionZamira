/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.sistemagestionzamira.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ilse_
 */
public class EnvioDAO {
      public List<Envio> obtenerEnvios() {
        List<Envio> lista = new ArrayList<>();
        String sql = "SELECT * FROM ENVIO";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Envio(
                    rs.getInt("id_envio"),
                    rs.getInt("id_pedido"),
                    rs.getString("metodo_entrega"),
                    rs.getString("direccion_entrega"),
                    rs.getString("estado"),
                    rs.getDate("fecha_estimada") != null ? rs.getDate("fecha_estimada").toLocalDate() : null
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void insertarEnvio(Envio envio) {
        String sql = "INSERT INTO ENVIO (id_envio, id_pedido, metodo_entrega, direccion_entrega, estado, fecha_estimada) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, envio.getIdEnvio());
            stmt.setInt(2, envio.getIdPedido());
            stmt.setString(3, envio.getMetodoEntrega());
            stmt.setString(4, envio.getDireccionEntrega());
            stmt.setString(5, envio.getEstado());
            if (envio.getFechaEstimada() != null) {
                stmt.setDate(6, Date.valueOf(envio.getFechaEstimada()));
            } else {
                stmt.setNull(6, Types.DATE);
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
