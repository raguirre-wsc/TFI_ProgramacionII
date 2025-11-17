/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectotfi.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import proyectotfi.entities.enums.Empresa;
import proyectotfi.entities.Envio;
import proyectotfi.entities.enums.EstadoEnvio;
import proyectotfi.entities.enums.TipoEnvio;

/**
 *
 * @author solyo
 */
public class EnvioDAO implements GenericDAO<Envio> {

    @Override
    public void crear(Envio envio, Connection conn) throws SQLException {
        
        String sql = "INSERT INTO envio (tracking, empresa, tipo, costo, fechaDespacho, fechaEstimada, estado, eliminado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, envio.getTracking());
            ps.setString(2, envio.getEmpresa().name());
            ps.setString(3, envio.getTipo().name());
            ps.setDouble(4, envio.getCosto());
            ps.setDate(5, Date.valueOf(envio.getFechaDespacho()));
            ps.setDate(6, Date.valueOf(envio.getFechaEstimada()));
            ps.setString(7, envio.getEstado().name());
            ps.setBoolean(8, envio.isEliminado());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    envio.setId(rs.getLong(1));
                }
            }
        }
    }

    @Override
    public Envio leer(Long id, Connection conn) throws SQLException {
        String sql = "SELECT * FROM envio WHERE id = ? AND eliminado = FALSE";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearEnvio(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Envio> leerTodos(Connection conn) throws SQLException {
        List<Envio> lista = new ArrayList<>();
        String sql = "SELECT * FROM envio WHERE eliminado = FALSE";
        try (PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearEnvio(rs));
            }
        }
        return lista;
    }

    @Override
    public void actualizar(Envio envio, Connection conn) throws SQLException {
        String sql = "UPDATE envio SET tracking=?, empresa=?, tipo=?, costo=?, fechaDespacho=?, fechaEstimada=?, estado=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, envio.getTracking());
            ps.setString(2, envio.getEmpresa().name());
            ps.setString(3, envio.getTipo().name());
            ps.setDouble(4, envio.getCosto());
            ps.setDate(5, Date.valueOf(envio.getFechaDespacho()));
            ps.setDate(6, Date.valueOf(envio.getFechaEstimada()));
            ps.setString(7, envio.getEstado().name());
            ps.setLong(8, envio.getId());
            ps.executeUpdate(); 
        }
    }

    @Override
    public void eliminar(Long id, Connection conn) throws SQLException {
        String sql = "UPDATE envio SET eliminado = TRUE WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

        private Envio mapearEnvio(ResultSet rs) throws SQLException {
        Envio e = new Envio();
        e.setId(rs.getLong("id"));
        e.setTracking(rs.getString("tracking"));
        e.setEmpresa(Empresa.valueOf(rs.getString("empresa")));
        e.setTipo(TipoEnvio.valueOf(rs.getString("tipo")));
        e.setCosto(rs.getDouble("costo"));
        e.setFechaDespacho(rs.getDate("fechaDespacho").toLocalDate());
        e.setFechaEstimada(rs.getDate("fechaEstimada").toLocalDate());
        e.setEstado(EstadoEnvio.valueOf(rs.getString("estado")));
        e.setEliminado(rs.getBoolean("eliminado"));
        return e;
    }
}
