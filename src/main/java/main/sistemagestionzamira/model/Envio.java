/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.sistemagestionzamira.model;

import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author ilse_
 */
public class Envio {
    private int idEnvio;
    private int idPedido;
    private String metodoEntrega;
    private String direccionEntrega;
    private String estado;
    private LocalDate fechaEstimada;

    public Envio(int idEnvio, int idPedido, String metodoEntrega, String direccionEntrega, String estado, LocalDate fechaEstimada) {
        this.idEnvio = idEnvio;
        this.idPedido = idPedido;
        this.metodoEntrega = metodoEntrega;
        this.direccionEntrega = direccionEntrega;
        this.estado = estado;
        this.fechaEstimada = fechaEstimada;
    }

    public int getIdEnvio() { return idEnvio; }
    public void setIdEnvio(int idEnvio) { this.idEnvio = idEnvio; }

    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }

    public String getMetodoEntrega() { return metodoEntrega; }
    public void setMetodoEntrega(String metodoEntrega) { this.metodoEntrega = metodoEntrega; }

    public String getDireccionEntrega() { return direccionEntrega; }
    public void setDireccionEntrega(String direccionEntrega) { this.direccionEntrega = direccionEntrega; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDate getFechaEstimada() { return fechaEstimada; }
    public void setFechaEstimada(LocalDate fechaEstimada) { this.fechaEstimada = fechaEstimada; }
}
