/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.sistemagestionzamira.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 *
 * @author ilse_
 */

public class Usuario {
    private final SimpleIntegerProperty idUsuario;
    private final SimpleStringProperty nombre;

    public Usuario(int idUsuario, String nombre) {
        this.idUsuario = new SimpleIntegerProperty(idUsuario);
        this.nombre = new SimpleStringProperty(nombre);
    }

    public int getIdUsuario() {
        return idUsuario.get();
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario.set(idUsuario);
    }

    public IntegerProperty idUsuarioProperty() {
        return idUsuario;
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public StringProperty nombreProperty() {
        return nombre;
    }
}

