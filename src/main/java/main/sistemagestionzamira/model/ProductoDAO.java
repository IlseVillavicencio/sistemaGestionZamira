/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.sistemagestionzamira.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ilse_
 */
public class ProductoDAO {
   // Obtener todos los productos
    public List<Producto> obtenerTodos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT nombre_producto, precio FROM PRODUCTO";

        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String nombre = rs.getString("nombre_producto");
                double precio = rs.getDouble("precio");
                productos.add(new Producto(nombre, precio));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }

    // Insertar un nuevo producto
    public boolean insertar(Producto producto) {
        String sql = "INSERT INTO PRODUCTO (nombre_producto, precio) VALUES (?, ?)";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, producto.getNombre());
            pstmt.setDouble(2, producto.getPrecio());

            int filas = pstmt.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Opcional: buscar por nombre
    public Producto buscarPorNombre(String nombre) {
        String sql = "SELECT nombre_producto, precio FROM PRODUCTO WHERE nombre_producto = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Producto(rs.getString("nombre_producto"), rs.getDouble("precio"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
