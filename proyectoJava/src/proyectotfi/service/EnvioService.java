/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectotfi.service;

/**
 *
 * @author solyo
 */
import proyectotfi.dao.EnvioDAO;
import proyectotfi.entities.Envio;
import proyectotfi.config.DatabaseConnection;
import java.sql.Connection;
import java.util.List;

public class EnvioService implements GenericService<Envio> {

    private final EnvioDAO envioDAO = new EnvioDAO();

    @Override
    public void insertar(Envio envio) throws Exception {
        validarEnvio(envio);

        try (Connection conn = DatabaseConnection.getConnection()) {
            envioDAO.crear(envio, conn);
        }
    }

    @Override
    public Envio getById(Long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return envioDAO.leer(id, conn);
        }
    }

    @Override
    public List<Envio> getAll() throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return envioDAO.leerTodos(conn);
        }
    }

    @Override
    public void actualizar(Envio envio) throws Exception {
        validarEnvio(envio);

        try (Connection conn = DatabaseConnection.getConnection()) {
            envioDAO.actualizar(envio, conn);
        }
    }

    @Override
    public void eliminar(Long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            envioDAO.eliminar(id, conn);
        }
    }

    // VALIDACIONES
    private void validarEnvio(Envio e) throws Exception {
        if (e == null)
            throw new Exception("El envío no puede ser null.");

        if (e.getTracking() == null || e.getTracking().isBlank())
            throw new Exception("El tracking no puede ser vacío.");

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
            throw new Exception("Debe seleccionar un estado de envío.");
    }
}