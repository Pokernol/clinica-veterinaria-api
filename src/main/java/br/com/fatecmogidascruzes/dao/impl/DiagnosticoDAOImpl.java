package br.com.fatecmogidascruzes.dao.impl;

import br.com.fatecmogidascruzes.dao.DiagnosticoDAO;
import br.com.fatecmogidascruzes.entities.Diagnostico;
import br.com.fatecmogidascruzes.factories.ConnectionFactorySingleton;
import br.com.fatecmogidascruzes.builders.DiagnosticoBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DiagnosticoDAOImpl implements DiagnosticoDAO {

    private static final Logger logger = LoggerFactory.getLogger(DiagnosticoDAOImpl.class);
    private final ConnectionFactorySingleton connectionFactory;

    public DiagnosticoDAOImpl(ConnectionFactorySingleton connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void add(Diagnostico diagnostico) throws SQLException {
        String sql = "INSERT INTO Diagnosticos (petId, consultaId, descricao, gravidade) VALUES (?, ?, ?, ?)";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, diagnostico.getPetId());
            if (diagnostico.getConsultaId() == 0) {
                stmt.setNull(2, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(2, diagnostico.getConsultaId());
            }
            stmt.setString(3, diagnostico.getDescricao());
            stmt.setString(4, diagnostico.getGravidade());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                logger.error("Falha ao registrar Diagnóstico, nenhuma linha afetada para petId: {}, consultaId: {}",
                        diagnostico.getPetId(), diagnostico.getConsultaId());
                throw new SQLException("Falha ao registrar Diagnóstico, nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    diagnostico.setId(generatedKeys.getInt(1));
                } else {
                    logger.error("Falha ao registrar Diagnóstico, nenhum ID obtido para petId: {}, consultaId: {}",
                            diagnostico.getPetId(), diagnostico.getConsultaId());
                    throw new SQLException("Falha ao registrar Diagnóstico, nenhum ID obtido.");
                }
            }
        } catch (SQLException e) {
            logger.error("Erro SQL inesperado ao adicionar diagnóstico para petId {}: SQLState: {} - Mensagem: {}",
                    diagnostico.getPetId(), e.getSQLState(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void update(Diagnostico diagnostico) throws SQLException {
        String sql = "UPDATE Diagnosticos SET petId = ?, consultaId = ?, descricao = ?, gravidade = ? WHERE id = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, diagnostico.getPetId());
            if (diagnostico.getConsultaId() == 0) {
                stmt.setNull(2, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(2, diagnostico.getConsultaId());
            }
            stmt.setString(3, diagnostico.getDescricao());
            stmt.setString(4, diagnostico.getGravidade());
            stmt.setInt(5, diagnostico.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                logger.warn("Tentativa de atualizar Diagnóstico ID {} sem encontrar registro.", diagnostico.getId());
            }
        } catch (SQLException e) {
            logger.error("Erro SQL inesperado ao atualizar diagnóstico ID {}: SQLState: {} - Mensagem: {}",
                    diagnostico.getId(), e.getSQLState(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Diagnosticos WHERE id = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                logger.warn("Tentativa de excluir Diagnóstico ID {} sem encontrar registro.", id);
            }
        } catch (SQLException e) {
            logger.error("Erro SQL inesperado ao excluir diagnóstico ID {}: SQLState: {} - Mensagem: {}",
                    id, e.getSQLState(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Diagnostico findById(int id) throws SQLException {
        String sql = "SELECT id, petId, consultaId, descricao, gravidade FROM Diagnosticos WHERE id = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new DiagnosticoBuilder()
                            .withId(rs.getInt("id"))
                            .withPetId(rs.getInt("petId"))
                            .withConsultaId(rs.getInt("consultaId"))
                            .withDescricao(rs.getString("descricao"))
                            .withGravidade(rs.getString("gravidade"))
                            .build();
                }
            }
        } catch (SQLException e) {
            logger.error("Erro SQL inesperado ao buscar diagnóstico por ID {}: SQLState: {} - Mensagem: {}",
                    id, e.getSQLState(), e.getMessage(), e);
            throw e;
        }
        return null;
    }

    @Override
    public List<Diagnostico> findAll() throws SQLException {
        List<Diagnostico> diagnosticos = new ArrayList<>();
        String sql = "SELECT id, petId, consultaId, descricao, gravidade FROM Diagnosticos";
        try (Connection conn = connectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                diagnosticos.add(new DiagnosticoBuilder()
                        .withId(rs.getInt("id"))
                        .withPetId(rs.getInt("petId"))
                        .withConsultaId(rs.getInt("consultaId"))
                        .withDescricao(rs.getString("descricao"))
                        .withGravidade(rs.getString("gravidade"))
                        .build());
            }
        } catch (SQLException e) {
            logger.error("Erro SQL inesperado ao buscar todos os diagnósticos: SQLState: {} - Mensagem: {}",
                    e.getSQLState(), e.getMessage(), e);
            throw e;
        }
        return diagnosticos;
    }

    @Override
    public List<Diagnostico> findByPetId(int petId) throws SQLException {
        List<Diagnostico> diagnosticos = new ArrayList<>();
        String sql = "SELECT id, petId, consultaId, descricao, gravidade FROM Diagnosticos WHERE petId = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, petId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    diagnosticos.add(new DiagnosticoBuilder()
                            .withId(rs.getInt("id"))
                            .withPetId(rs.getInt("petId"))
                            .withConsultaId(rs.getInt("consultaId"))
                            .withDescricao(rs.getString("descricao"))
                            .withGravidade(rs.getString("gravidade"))
                            .build());
                }
            }
        } catch (SQLException e) {
            logger.error("Erro SQL inesperado ao buscar diagnósticos por Pet ID {}: SQLState: {} - Mensagem: {}",
                    petId, e.getSQLState(), e.getMessage(), e);
            throw e;
        }
        return diagnosticos;
    }

    @Override
    public List<Diagnostico> findByConsultaId(int consultaId) throws SQLException {
        List<Diagnostico> diagnosticos = new ArrayList<>();
        String sql = "SELECT id, petId, consultaId, descricao, gravidade FROM Diagnosticos WHERE consultaId = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, consultaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    diagnosticos.add(new DiagnosticoBuilder()
                            .withId(rs.getInt("id"))
                            .withPetId(rs.getInt("petId"))
                            .withConsultaId(rs.getInt("consultaId"))
                            .withDescricao(rs.getString("descricao"))
                            .withGravidade(rs.getString("gravidade"))
                            .build());
                }
            }
        } catch (SQLException e) {
            logger.error("Erro SQL inesperado ao buscar diagnósticos por Consulta ID {}: SQLState: {} - Mensagem: {}",
                    consultaId, e.getSQLState(), e.getMessage(), e);
            throw e;
        }
        return diagnosticos;
    }
}