/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectotfi.entities;

/**
 *
 * @author rodrigo_aguirre
 */

import java.time.LocalDate;

/**
 * Clase que representa un Envio dentro del sistema.
 * 
 *  - Contiene un atributo id (tipo Long).
 *  - Tiene un campo eliminado (Boolean) para borrado lógico.
 *  - Define atributos propios del envio (tracking, empresa, tipo, costo, fechaDespacho, fechaEstimada, estado).
 *  - Mantiene una relación 1 a 1 con la clase Envio.
 *  - Incluye constructores, getters/setters y un toString() legible.
 */

public class Envio {

// Definimos atributos de clase.
    private Long id;
    private Boolean eliminado;
    private String tracking;
    private Empresa empresa;
    private TipoEnvio tipo;
    private double costo;
    private LocalDate fechaDespacho;
    private LocalDate fechaEstimada;
    private EstadoEnvio estado;


// Constructor vacío (requerido por frameworks y para instanciación flexible).
    public Envio() {
    }

// Constructor completo: inicializa todos los campos de la clase.
    public Envio(Long id, Boolean eliminado, String tracking, Empresa empresa,
                 TipoEnvio tipo, double costo, LocalDate fechaDespacho,
                 LocalDate fechaEstimada, EstadoEnvio estado) {
        this.id = id;
        this.eliminado = eliminado;
        this.tracking = tracking;
        this.empresa = empresa;
        this.tipo = tipo;
        this.costo = costo;
        this.fechaDespacho = fechaDespacho;
        this.fechaEstimada = fechaEstimada;
        this.estado = estado;
    }

// --- Métodos getters y setters ---
// Se utilizan para acceder y modificar los valores de los atributos privados.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }

    public String getTracking() {
        return tracking;
    }

    public void setTracking(String tracking) {
        this.tracking = tracking;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public TipoEnvio getTipo() {
        return tipo;
    }

    public void setTipo(TipoEnvio tipo) {
        this.tipo = tipo;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public LocalDate getFechaDespacho() {
        return fechaDespacho;
    }

    public void setFechaDespacho(LocalDate fechaDespacho) {
        this.fechaDespacho = fechaDespacho;
    }

    public LocalDate getFechaEstimada() {
        return fechaEstimada;
    }

    public void setFechaEstimada(LocalDate fechaEstimada) {
        this.fechaEstimada = fechaEstimada;
    }

    public EstadoEnvio getEstado() {
        return estado;
    }

    public void setEstado(EstadoEnvio estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Envio{" +
                "id=" + id +
                ", eliminado=" + eliminado +
                ", tracking='" + tracking + '\'' +
                ", empresa=" + empresa +
                ", tipo=" + tipo +
                ", costo=" + costo +
                ", fechaDespacho=" + fechaDespacho +
                ", fechaEstimada=" + fechaEstimada +
                ", estado=" + estado +
                '}';
    }
}