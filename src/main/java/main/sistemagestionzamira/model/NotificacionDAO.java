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
public class NotificacionDAO {
    public List<Notificacion> obtenerNotificaciones() {
        List<Notificacion> lista = new ArrayList<>();
        String sql = "SELECT * FROM NOTIFICACION";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Notificacion(
                    rs.getInt("id_notificacion"),
                    rs.getInt("id_usuario"),
                    rs.getString("mensaje"),
                    rs.getTimestamp("fecha").toLocalDateTime(),
                    rs.getBoolean("leida")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void insertarNotificacion(Notificacion noti) {
        String sql = "INSERT INTO NOTIFICACION (id_notificacion, id_usuario, mensaje, fecha, leida) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, noti.getIdNotificacion());
            stmt.setInt(2, noti.getIdUsuario());
            stmt.setString(3, noti.getMensaje());
            stmt.setTimestamp(4, Timestamp.valueOf(noti.getFecha()));
            stmt.setBoolean(5, noti.isLeida());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
