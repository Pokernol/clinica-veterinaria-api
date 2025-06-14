package br.com.fatecmogidascruzes.dao.impl;

import br.com.fatecmogidascruzes.dao.ConsultaDAO;
import br.com.fatecmogidascruzes.entities.Consulta;
import br.com.fatecmogidascruzes.factories.ConnectionFactorySingleton;
import br.com.fatecmogidascruzes.builders.ConsultaBuilder;
import br.com.fatecmogidascruzes.exceptions.DadoDuplicadoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAOImpl implements ConsultaDAO {

    private static final Logger logger = LoggerFactory.getLogger(ConsultaDAOImpl.class);
    private final ConnectionFactorySingleton connectionFactory;

    public ConsultaDAOImpl(ConnectionFactorySingleton connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void add(Consulta consulta) throws SQLException {
        String sql = "INSERT INTO Consultas (petId, veterinarioId, dataHora, motivoConsulta) VALUES (?, ?, ?, ?)";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, consulta.getPetId());
            stmt.setInt(2, consulta.getVeterinarioId());
            stmt.setTimestamp(3, Timestamp.valueOf(consulta.getDataHora()));
            stmt.setString(4, consulta.getMotivoConsulta());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                logger.error("Falha ao agendar Consulta, nenhuma linha afetada para: {}", consulta);
                throw new SQLException("Falha ao agendar Consulta, nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    consulta.setId(generatedKeys.getInt(1));
                } else {
                    logger.error("Falha ao agendar Consulta, nenhum ID obtido para: {}", consulta);
                    throw new SQLException("Falha ao agendar Consulta, nenhum ID obtido.");
                }
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                logger.warn("Tentativa de agendar consulta em horário já ocupado para o veterinário ID {} na data/hora {}. SQLState: {}. Mensagem: {}",
                        consulta.getVeterinarioId(), consulta.getDataHora(), e.getSQLState(), e.getMessage());
                throw new DadoDuplicadoException("Já existe uma consulta agendada para este veterinário neste horário.", e);
            } else if (e.getSQLState().equals("23503")) {
                logger.warn("Tentativa de agendar consulta com ID de Pet ou Veterinário inexistente. SQLState: {}. Mensagem: {}",
                        e.getSQLState(), e.getMessage(), e);
                throw new SQLException("Não foi possível agendar a consulta. Verifique se o Pet e o Veterinário existem.", e);
            }
            else {
                logger.error("Erro SQL inesperado ao adicionar consulta {}: SQLState: {} - Mensagem: {}",
                        consulta.getDataHora(), e.getSQLState(), e.getMessage(), e);
                throw e;
            }
        }
    }

    @Override
    public void update(Consulta consulta) throws SQLException {
        String sql = "UPDATE Consultas SET petId = ?, veterinarioId = ?, dataHora = ?, motivoConsulta = ?, diagnostico = ?, prescricaoId = ? WHERE id = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, consulta.getPetId());
            stmt.setInt(2, consulta.getVeterinarioId());
            stmt.setTimestamp(3, Timestamp.valueOf(consulta.getDataHora()));
            stmt.setString(4, consulta.getMotivoConsulta());
            stmt.setString(5, consulta.getDiagnostico());

            if (consulta.getPrescricaoId() == 0) {
                stmt.setNull(6, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(6, consulta.getPrescricaoId());
            }
            stmt.setInt(7, consulta.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                logger.warn("Tentativa de atualizar Consulta ID {} sem encontrar registro.", consulta.getId());
                throw new SQLException("Nenhuma consulta encontrada com o ID fornecido para atualização.");
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                logger.warn("Tentativa de atualizar Consulta para horário já ocupado para o veterinário ID {} na data/hora {}. SQLState: {}. Mensagem: {}",
                        consulta.getVeterinarioId(), consulta.getDataHora(), e.getSQLState(), e.getMessage());
                throw new DadoDuplicadoException("Não foi possível atualizar a consulta: este horário já está ocupado para o veterinário.", e);
            } else if (e.getSQLState().equals("23503")) {
                logger.warn("Tentativa de atualizar consulta com ID de Pet, Veterinário ou Prescrição inexistente. SQLState: {}. Mensagem: {}",
                        e.getSQLState(), e.getMessage(), e);
                throw new SQLException("Não foi possível atualizar a consulta. Verifique os IDs de Pet, Veterinário ou Prescrição.", e);
            } else {
                logger.error("Erro SQL inesperado ao atualizar consulta ID {}: SQLState: {} - Mensagem: {}",
                        consulta.getId(), e.getSQLState(), e.getMessage(), e);
                throw e;
            }
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Consultas WHERE id = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                logger.warn("Tentativa de excluir Consulta ID {} sem encontrar registro.", id);
                throw new SQLException("Nenhuma consulta encontrada com o ID fornecido para exclusão.");
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals("23503")) {
                logger.warn("Tentativa de excluir Consulta ID {} que possui registros dependentes. SQLState: {}. Mensagem: {}",
                        id, e.getSQLState(), e.getMessage());
                throw new SQLException("Não foi possível excluir a consulta, pois existem registros (diagnósticos, prescrições) que dependem dela.", e);
            } else {
                logger.error("Erro SQL inesperado ao excluir consulta ID {}: SQLState: {} - Mensagem: {}",
                        id, e.getSQLState(), e.getMessage(), e);
                throw e;
            }
        }
    }

    @Override
    public Consulta findById(int id) throws SQLException {
        String sql = "SELECT id, petId, veterinarioId, dataHora, motivoConsulta, diagnostico, prescricaoId FROM Consultas WHERE id = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new ConsultaBuilder()
                            .withId(rs.getInt("id"))
                            .withPetId(rs.getInt("petId"))
                            .withVeterinarioId(rs.getInt("veterinarioId"))
                            .withDataHora(rs.getTimestamp("dataHora").toLocalDateTime())
                            .withMotivoConsulta(rs.getString("motivoConsulta"))
                            .withDiagnostico(rs.getString("diagnostico"))
                            .withPrescricaoId(rs.getInt("prescricaoId"))
                            .build();
                }
            }
        } catch (SQLException e) {
            logger.error("Erro SQL inesperado ao buscar consulta por ID {}: SQLState: {} - Mensagem: {}",
                    id, e.getSQLState(), e.getMessage(), e);
            throw e;
        }
        return null;
    }

    @Override
    public List<Consulta> findAll() throws SQLException {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT id, petId, veterinarioId, dataHora, motivoConsulta, diagnostico, prescricaoId FROM Consultas";
        try (Connection conn = connectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                consultas.add(new ConsultaBuilder()
                        .withId(rs.getInt("id"))
                        .withPetId(rs.getInt("petId"))
                        .withVeterinarioId(rs.getInt("veterinarioId"))
                        .withDataHora(rs.getTimestamp("dataHora").toLocalDateTime())
                        .withMotivoConsulta(rs.getString("motivoConsulta"))
                        .withDiagnostico(rs.getString("diagnostico"))
                        .withPrescricaoId(rs.getInt("prescricaoId"))
                        .build());
            }
        } catch (SQLException e) {
            logger.error("Erro SQL inesperado ao buscar todas as consultas: SQLState: {} - Mensagem: {}",
                    e.getSQLState(), e.getMessage(), e);
            throw e;
        }
        return consultas;
    }

    @Override
    public List<Consulta> findByPetId(int petId) throws SQLException {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT id, petId, veterinarioId, dataHora, motivoConsulta, diagnostico, prescricaoId FROM Consultas WHERE petId = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, petId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    consultas.add(new ConsultaBuilder()
                            .withId(rs.getInt("id"))
                            .withPetId(rs.getInt("petId"))
                            .withVeterinarioId(rs.getInt("veterinarioId"))
                            .withDataHora(rs.getTimestamp("dataHora").toLocalDateTime())
                            .withMotivoConsulta(rs.getString("motivoConsulta"))
                            .withDiagnostico(rs.getString("diagnostico"))
                            .withPrescricaoId(rs.getInt("prescricaoId"))
                            .build());
                }
            }
        } catch (SQLException e) {
            logger.error("Erro SQL inesperado ao buscar consultas por Pet ID {}: SQLState: {} - Mensagem: {}",
                    petId, e.getSQLState(), e.getMessage(), e);
            throw e;
        }
        return consultas;
    }

    @Override
    public List<Consulta> findByVeterinarioId(int veterinarioId) throws SQLException {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT id, petId, veterinarioId, dataHora, motivoConsulta, diagnostico, prescricaoId FROM Consultas WHERE veterinarioId = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, veterinarioId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    consultas.add(new ConsultaBuilder()
                            .withId(rs.getInt("id"))
                            .withPetId(rs.getInt("petId"))
                            .withVeterinarioId(rs.getInt("veterinarioId"))
                            .withDataHora(rs.getTimestamp("dataHora").toLocalDateTime())
                            .withMotivoConsulta(rs.getString("motivoConsulta"))
                            .withDiagnostico(rs.getString("diagnostico"))
                            .withPrescricaoId(rs.getInt("prescricaoId"))
                            .build());
                }
            }
        } catch (SQLException e) {
            logger.error("Erro SQL inesperado ao buscar consultas por Veterinário ID {}: SQLState: {} - Mensagem: {}",
                    veterinarioId, e.getSQLState(), e.getMessage(), e);
            throw e;
        }
        return consultas;
    }
}