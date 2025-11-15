/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectotfi.service;

/**
 *
 * @author solyo
 */
import proyectotfi.config.DatabaseConnection;
import proyectotfi.dao.EnvioDAO;
import proyectotfi.dao.PedidoDAO;
import proyectotfi.entities.Envio;
import proyectotfi.entities.Pedido;

import java.sql.Connection;
import java.util.List;

public class PedidoService implements GenericService<Pedido> {

    private final PedidoDAO pedidoDAO = new PedidoDAO();
    private final EnvioDAO envioDAO = new EnvioDAO();

    //  INSERTAR PEDIDO 
    @Override
    public void insertar(Pedido pedido) throws Exception {

        validarPedido(pedido);
        validarEnvio(pedido.getEnvio());

        Connection conn = null;

        try {
            conn = DatabaseConnection.getConnection();

            // Iniciar transacción
            conn.setAutoCommit(false);

            // Crear el envío
            envioDAO.crear(pedido.getEnvio(), conn);

            // Asociar el envío al pedido
            pedido.setEnvio(pedido.getEnvio());

            // Crear el pedido
            pedidoDAO.crear(pedido, conn);

            // si todo sale bien se ejecuta commit
            conn.commit();

        } catch (Exception e) {

            // Si algo sale mal se ejecuta rollback
            if (conn != null) {
                conn.rollback();
            }
            throw new Exception("No se pudo crear el pedido: " + e.getMessage());

        } finally {

            // Restablecer autocommit y cerrar conexión
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    //  OBTENER POR ID
    @Override
    public Pedido getById(Long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return pedidoDAO.leer(id, conn);
        }
    }

    //  LISTAR TODOS
    @Override
    public List<Pedido> getAll() throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return pedidoDAO.leerTodos(conn);
        }
    }

    //  ACTUALIZAR PEDIDO  
    @Override
    public void actualizar(Pedido pedido) throws Exception {

        validarPedido(pedido);
        validarEnvio(pedido.getEnvio());

        Connection conn = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // iniciar transacción

            // Actualizar el envío
            envioDAO.actualizar(pedido.getEnvio(), conn);

            // Actualizar pedido
            pedidoDAO.actualizar(pedido, conn);

            conn.commit();

        } catch (Exception e) {

            if (conn != null) conn.rollback();
            throw new Exception("No se pudo actualizar el pedido: " + e.getMessage());

        } finally {

            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    //  ELIMINAR PEDIDO 
    @Override
    public void eliminar(Long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            pedidoDAO.eliminar(id, conn);
        }
    }

    //  VALIDACIONES DE PEDIDO
    private void validarPedido(Pedido p) throws Exception {

        if (p == null)
            throw new Exception("El pedido no puede ser null.");

        if (p.getNumero() == null || p.getNumero().isBlank())
            throw new Exception("El número del pedido es obligatorio.");

        if (p.getFecha() == null)
            throw new Exception("La fecha del pedido es obligatoria.");

        if (p.getClienteNombre() == null || p.getClienteNombre().isBlank())
            throw new Exception("El nombre del cliente es obligatorio.");

        if (p.getTotal() < 0)
            throw new Exception("El total no puede ser negativo.");

        if (p.getEstado() == null)
            throw new Exception("Debe seleccionar un estado para el pedido.");

        if (p.getEnvio() == null)
            throw new Exception("Todo pedido debe tener un envío asociado.");
    }

    //  VALIDACIONES DE ENVÍO (desde PedidoService)
    private void validarEnvio(Envio e) throws Exception {

        if (e.getTracking() == null || e.getTracking().isBlank())
            throw new Exception("El tracking del envío es obligatorio.");

        if (e.getEmpresa() == null)
            throw new Exception("Debe seleccionar una empresa de envío.");

        if (e.getTipo() == null)
            throw new Exception("Debe seleccionar un tipo de envío.");

        if (e.getFechaDespacho() == null)
            throw new Exception("La fecha de despacho es obligatoria.");

        if (e.getFechaEstimada() == null)
            throw new Exception("La fecha estimada es obligatoria.");

        if (e.getFechaEstimada().isBefore(e.getFechaDespacho()))
            throw new Exception("La fecha estimada no puede ser anterior a la fecha de despacho.");

        if (e.getCosto() < 0)
            throw new Exception("El costo no puede ser negativo.");

        if (e.getEstado() == null)
            throw new Exception("Debe seleccionar un estado para el envío.");
    }
}