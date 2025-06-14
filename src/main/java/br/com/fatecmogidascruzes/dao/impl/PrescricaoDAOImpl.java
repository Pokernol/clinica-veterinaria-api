package br.com.fatecmogidascruzes.dao.impl;

import br.com.fatecmogidascruzes.dao.PrescricaoDAO;
import br.com.fatecmogidascruzes.entities.Prescricao;
import br.com.fatecmogidascruzes.factories.ConnectionFactorySingleton;
import br.com.fatecmogidascruzes.builders.PrescricaoBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PrescricaoDAOImpl implements PrescricaoDAO {

    private static final Logger logger = LoggerFactory.getLogger(PrescricaoDAOImpl.class);
    private final ConnectionFactorySingleton connectionFactory;

    public PrescricaoDAOImpl(ConnectionFactorySingleton connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void add(Prescricao prescricao) throws SQLException {
        String sql = "INSERT INTO Prescricoes (petId, veterinarioId, consultaId, medicamento, dosagem, instrucoesUso) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, prescricao.getPetId());
            stmt.setInt(2, prescricao.getVeterinarioId());
            if (prescricao.getConsultaId() == 0) {
                stmt.setNull(3, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(3, prescricao.getConsultaId());
            }
            stmt.setString(4, prescricao.getMedicamento());
            stmt.setString(5, prescricao.getDosagem());
            stmt.setString(6, prescricao.getInstrucoesUso());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                logger.error("Falha ao registrar Prescrição, nenhuma linha afetada para: {}", prescricao);
                throw new SQLException("Falha ao registrar Prescrição, nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    prescricao.setId(generatedKeys.getInt(1));
                } else {
                    logger.error("Falha ao registrar Prescrição, nenhum ID obtido para: {}", prescricao);
                    throw new SQLException("Falha ao registrar Prescrição, nenhum ID obtido.");
                }
            }
        } catch (SQLException e) {
            logger.error("Erro SQL inesperado ao adicionar prescrição {}: SQLState: {} - Mensagem: {}",
                    prescricao.getMedicamento(), e.getSQLState(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void update(Prescricao prescricao) throws SQLException {
        String sql = "UPDATE Prescricoes SET petId = ?, veterinarioId = ?, consultaId = ?, medicamento = ?, dosagem = ?, instrucoesUso = ? WHERE id = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, prescricao.getPetId());
            stmt.setInt(2, prescricao.getVeterinarioId());
            if (prescricao.getConsultaId() == 0) {
                stmt.setNull(3, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(3, prescricao.getConsultaId());
            }
            stmt.setString(4, prescricao.getMedicamento());
            stmt.setString(5, prescricao.getDosagem());
            stmt.setString(6, prescricao.getInstrucoesUso());
            stmt.setInt(7, prescricao.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                logger.warn("Tentativa de atualizar Prescrição ID {} sem encontrar registro.", prescricao.getId());
                throw new SQLException("Nenhuma prescrição encontrada com o ID fornecido para atualização.");
            }
        } catch (SQLException e) {
            logger.error("Erro SQL inesperado ao atualizar prescrição ID {}: SQLState: {} - Mensagem: {}",
                    prescricao.getId(), e.getSQLState(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Prescricoes WHERE id = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                logger.warn("Tentativa de excluir Prescrição ID {} sem encontrar registro.", id);
                throw new SQLException("Nenhuma prescrição encontrada com o ID fornecido para exclusão.");
            }
        } catch (SQLException e) {
            logger.error("Erro SQL inesperado ao excluir prescrição ID {}: SQLState: {} - Mensagem: {}",
                    id, e.getSQLState(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Prescricao findById(int id) throws SQLException {
        String sql = "SELECT id, petId, veterinarioId, consultaId, medicamento, dosagem, instrucoesUso FROM Prescricoes WHERE id = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new PrescricaoBuilder()
                            .withId(rs.getInt("id"))
                            .withPetId(rs.getInt("petId"))
                            .withVeterinarioId(rs.getInt("veterinarioId"))
                            .withConsultaId(rs.getInt("consultaId"))
                            .withMedicamento(rs.getString("medicamento"))
                            .withDosagem(rs.getString("dosagem"))
                            .withInstrucoesUso(rs.getString("instrucoesUso"))
                            .build();
                }
            }
        } catch (SQLException e) {
            logger.error("Erro SQL inesperado ao buscar prescrição por ID {}: SQLState: {} - Mensagem: {}",
                    id, e.getSQLState(), e.getMessage(), e);
            throw e;
        }
        return null;
    }

    @Override
    public List<Prescricao> findAll() throws SQLException {
        List<Prescricao> prescricoes = new ArrayList<>();
        String sql = "SELECT id, petId, veterinarioId, consultaId, medicamento, dosagem, instrucoesUso FROM Prescricoes";
        try (Connection conn = connectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                prescricoes.add(new PrescricaoBuilder()
                        .withId(rs.getInt("id"))
                        .withPetId(rs.getInt("petId"))
                        .withVeterinarioId(rs.getInt("veterinarioId"))
                        .withConsultaId(rs.getInt("consultaId"))
                        .withMedicamento(rs.getString("medicamento"))
                        .withDosagem(rs.getString("dosagem"))
                        .withInstrucoesUso(rs.getString("instrucoesUso"))
                        .build());
            }
        } catch (SQLException e) {
            logger.error("Erro SQL inesperado ao buscar todas as prescrições: SQLState: {} - Mensagem: {}",
                    e.getSQLState(), e.getMessage(), e);
            throw e;
        }
        return prescricoes;
    }

    @Override
    public List<Prescricao> findByPetId(int petId) throws SQLException {
        List<Prescricao> prescricoes = new ArrayList<>();
        String sql = "SELECT id, petId, veterinarioId, consultaId, medicamento, dosagem, instrucoesUso FROM Prescricoes WHERE petId = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, petId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    prescricoes.add(new PrescricaoBuilder()
                            .withId(rs.getInt("id"))
                            .withPetId(rs.getInt("petId"))
                            .withVeterinarioId(rs.getInt("veterinarioId"))
                            .withConsultaId(rs.getInt("consultaId"))
                            .withMedicamento(rs.getString("medicamento"))
                            .withDosagem(rs.getString("dosagem"))
                            .withInstrucoesUso(rs.getString("instrucoesUso"))
                            .build());
                }
            }
        } catch (SQLException e) {
            logger.error("Erro SQL inesperado ao buscar prescrições por Pet ID {}: SQLState: {} - Mensagem: {}",
                    petId, e.getSQLState(), e.getMessage(), e);
            throw e;
        }
        return prescricoes;
    }

    @Override
    public List<Prescricao> findByVeterinarioId(int veterinarioId) throws SQLException {
        List<Prescricao> prescricoes = new ArrayList<>();
        String sql = "SELECT id, petId, veterinarioId, consultaId, medicamento, dosagem, instrucoesUso FROM Prescricoes WHERE veterinarioId = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, veterinarioId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    prescricoes.add(new PrescricaoBuilder()
                            .withId(rs.getInt("id"))
                            .withPetId(rs.getInt("petId"))
                            .withVeterinarioId(rs.getInt("veterinarioId"))
                            .withConsultaId(rs.getInt("consultaId"))
                            .withMedicamento(rs.getString("medicamento"))
                            .withDosagem(rs.getString("dosagem"))
                            .withInstrucoesUso(rs.getString("instrucoesUso"))
                            .build());
                }
            }
        } catch (SQLException e) {
            logger.error("Erro SQL inesperado ao buscar prescrições por Veterinário ID {}: SQLState: {} - Mensagem: {}",
                    veterinarioId, e.getSQLState(), e.getMessage(), e);
            throw e;
        }
        return prescricoes;
    }

    @Override
    public List<Prescricao> findByConsultaId(int consultaId) throws SQLException {
        List<Prescricao> prescricoes = new ArrayList<>();
        String sql = "SELECT id, petId, veterinarioId, consultaId, medicamento, dosagem, instrucoesUso FROM Prescricoes WHERE consultaId = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, consultaId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    prescricoes.add(new PrescricaoBuilder()
                            .withId(rs.getInt("id"))
                            .withPetId(rs.getInt("petId"))
                            .withVeterinarioId(rs.getInt("veterinarioId"))
                            .withConsultaId(rs.getInt("consultaId"))
                            .withMedicamento(rs.getString("medicamento"))
                            .withDosagem(rs.getString("dosagem"))
                            .withInstrucoesUso(rs.getString("instrucoesUso"))
                            .build());
                }
            }
        } catch (SQLException e) {
            logger.error("Erro SQL inesperado ao buscar prescrições por Consulta ID {}: SQLState: {} - Mensagem: {}",
                    consultaId, e.getSQLState(), e.getMessage(), e);
            throw e;
        }
        return prescricoes;
    }
}