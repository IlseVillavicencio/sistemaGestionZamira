/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemaventazamira.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ilse_
 */
public class ConexionDB {
    private static final String URL = "jdbc:mysql://yamabiko.proxy.rlwy.net:34163/railway";
    private static final String USER = "root";
    private static final String PASSWORD = "sFrdysrDfZtahYVhsdyzhNsKECijredS";

    public static Connection conectar() {
        try {
            // Registrar el driver (opcional desde JDBC 4.0)
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Driver JDBC no encontrado.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos:");
            e.printStackTrace();
        }
        return null;
    }
}
