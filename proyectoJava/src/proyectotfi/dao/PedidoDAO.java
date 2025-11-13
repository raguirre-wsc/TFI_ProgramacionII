/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectotfi.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import proyectotfi.entities.Pedido;
import proyectotfi.entities.enums.EstadoPedido;

/**
 *
 * @author solyo
 */
public class PedidoDAO implements GenericDAO<Pedido>{

    private EnvioDAO envioDao = new EnvioDAO();
    
    @Override
    public void crear(Pedido pedido, Connection conn) throws SQLException {
        envioDao.crear(pedido.getEnvio(), conn);
         String sql = "INSERT INTO pedido (numero, fecha, clienteNombre, total, estado, envio_id, eliminado) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, pedido.getNumero());
            ps.setDate(2, Date.valueOf(pedido.getFecha()));
            ps.setString(3, pedido.getClienteNombre());
            ps.setDouble(4, pedido.getTotal());
            ps.setString(5, pedido.getEstado().name());
            ps.setLong(6, pedido.getEnvio().getId());
            ps.setBoolean(7, pedido.isEliminado());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) pedido.setId(rs.getLong(1));
            }
        }
    }

    @Override
    public Pedido leer(Long id, Connection conn) throws SQLException {
        String sql = "SELECT * FROM pedido WHERE id = ? AND eliminado = FALSE";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Pedido p = mapearPedido(rs);
                    long envioId = rs.getLong("envio_id");
                    p.setEnvio(envioDao.leer(envioId, conn));
                    return p;
                }
            }
        }
        return null;
    }

    @Override
    public List<Pedido> leerTodos(Connection conn) throws SQLException {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedido WHERE eliminado = FALSE";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Pedido p = mapearPedido(rs);
                long envioId = rs.getLong("envio_id");
                p.setEnvio(envioDao.leer(envioId, conn));
                lista.add(p);
            }
        }
        return lista;
    }

    @Override
    public void actualizar(Pedido pedido, Connection conn) throws SQLException {
        envioDao.actualizar(pedido.getEnvio(), conn);

        String sql = "UPDATE pedido SET numero=?, fecha=?, clienteNombre=?, total=?, estado=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pedido.getNumero());
            ps.setDate(2, Date.valueOf(pedido.getFecha()));
            ps.setString(3, pedido.getClienteNombre());
            ps.setDouble(4, pedido.getTotal());
            ps.setString(5, pedido.getEstado().name());
            ps.setLong(6, pedido.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id, Connection conn) throws SQLException {
        String sql = "UPDATE pedido SET eliminado = TRUE WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    private Pedido mapearPedido(ResultSet rs) throws SQLException {
        Pedido p = new Pedido();
        p.setId(rs.getLong("id"));
        p.setNumero(rs.getString("numero"));
        p.setFecha(rs.getDate("fecha").toLocalDate());
        p.setClienteNombre(rs.getString("clienteNombre"));
        p.setTotal(rs.getDouble("total"));
        p.setEstado(EstadoPedido.valueOf(rs.getString("estado")));
        p.setEliminado(rs.getBoolean("eliminado"));
        return p;
    }
    
}
