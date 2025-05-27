/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.sistemagestionzamira.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author ilse_
 */
public class DatosDePrueba {
    public void insertarDatos() {
        try (Connection conn = ConexionDB.conectar()) {
            conn.setAutoCommit(false); // Para asegurar consistencia

            // Tabla USUARIO
            PreparedStatement ps1 = conn.prepareStatement(
                    "INSERT INTO USUARIO VALUES (1, 'Juan Pérez'), (2, 'Ana López')");
            ps1.executeUpdate();

            // Tabla CLIENTE
            PreparedStatement ps2 = conn.prepareStatement(
                    "INSERT INTO CLIENTE VALUES " +
                    "(1, 'Carlos Sánchez', 'carlos@gmail.com', '123456789'), " +
                    "(2, 'Lucía Martínez', 'lucia@gmail.com', '987654321')");
            ps2.executeUpdate();

            // Tabla CITA
            PreparedStatement ps3 = conn.prepareStatement(
                    "INSERT INTO CITA VALUES " +
                    "(1, 1, '2024-06-01', 'Consulta general'), " +
                    "(2, 2, '2024-06-02', 'Control rutinario')");
            ps3.executeUpdate();

            // Agrega aquí más tablas si lo necesitas...

            conn.commit();
            System.out.println("Datos de prueba insertados correctamente");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
