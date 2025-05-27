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
public class SoporteDAO {
    public List<Soporte> obtenerTickets() {
        List<Soporte> lista = new ArrayList<>();
        String sql = "SELECT * FROM SOPORTE";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Soporte(
                    rs.getInt("id_ticket"),
                    rs.getInt("id_usuario"),
                    rs.getString("asunto"),
                    rs.getString("descripcion"),
                    rs.getString("estado"),
                    rs.getTimestamp("fecha_creacion").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void insertarSoporte(Soporte ticket) {
        String sql = "INSERT INTO SOPORTE (id_ticket, id_usuario, asunto, descripcion, estado, fecha_creacion) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ticket.getIdTicket());
            stmt.setInt(2, ticket.getIdUsuario());
            stmt.setString(3, ticket.getAsunto());
            stmt.setString(4, ticket.getDescripcion());
            stmt.setString(5, ticket.getEstado());
            stmt.setTimestamp(6, Timestamp.valueOf(ticket.getFechaCreacion()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
