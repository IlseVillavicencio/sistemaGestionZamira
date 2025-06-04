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
        String sql = "SELECT id_producto, nombre, precio FROM PRODUCTO";

        try (Connection conn = ConexionDB.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int idProducto = rs.getInt("id_producto");
                String nombre = rs.getString("nombre");
                double precio = rs.getDouble("precio");
                productos.add(new Producto(idProducto, nombre, precio));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }

    // Insertar un nuevo producto
    public boolean insertar(Producto producto) {
        String sql = "INSERT INTO PRODUCTO (nombre, precio) VALUES (?, ?)";

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

    // Buscar por nombre
    public Producto buscarPorNombre(String nombre) {
        String sql = "SELECT id_producto, nombre, precio FROM PRODUCTO WHERE nombre = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nombre);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int idProducto = rs.getInt("id_producto");
                    double precio = rs.getDouble("precio");
                    return new Producto(idProducto, nombre, precio);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}