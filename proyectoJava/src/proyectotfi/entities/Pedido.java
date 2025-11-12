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
 * Clase que representa un Pedido dentro del sistema.
 * 
 *  - Contiene un atributo id (tipo Long).
 *  - Tiene un campo eliminado (Boolean) para borrado lógico.
 *  - Define atributos propios del pedido (número, fecha, clienteNombre, total, estado, envio).
 *  - Mantiene una relación 1 a 1 con la clase Envio.
 *  - Incluye constructores, getters/setters y un toString() legible.
 */

public class Pedido {

// Definimos atributos de clase.

    private Long id;
    private Boolean eliminado;
    private String numero;
    private LocalDate fecha;
    private String clienteNombre;
    private double total;
    private EstadoPedido estado;
    private Envio envio;


// Constructor vacío (requerido por frameworks y para instanciación flexible).

    public Pedido() {
    }


//Constructor completo: inicializa todos los campos de la clase.

    public Pedido(Long id, Boolean eliminado, String numero, LocalDate fecha,
                  String clienteNombre, double total, EstadoPedido estado, Envio envio) {
        this.id = id;
        this.eliminado = eliminado;
        this.numero = numero;
        this.fecha = fecha;
        this.clienteNombre = clienteNombre;
        this.total = total;
        this.estado = estado;
        this.envio = envio;
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

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public Envio getEnvio() {
        return envio;
    }

    public void setEnvio(Envio envio) {
        this.envio = envio;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", eliminado=" + eliminado +
                ", numero='" + numero + '\'' +
                ", fecha=" + fecha +
                ", clienteNombre='" + clienteNombre + '\'' +
                ", total=" + total +
                ", estado=" + estado +
                ", envio=" + envio +
                '}';
    }
}