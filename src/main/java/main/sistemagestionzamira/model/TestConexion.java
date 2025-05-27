/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.sistemagestionzamira.model;

import java.sql.Connection;

/**
 *
 * @author ilse_
 */
public class TestConexion {
    public static void main(String[] args) {
        Connection conn = ConexionDB.conectar();
        if (conn != null) {
            System.out.println("✅ Conexión exitosa");
        } else {
            System.out.println("❌ No se pudo conectar");
        }
    }
    
}
