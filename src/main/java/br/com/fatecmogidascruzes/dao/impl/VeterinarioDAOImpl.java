package br.com.fatecmogidascruzes.dao.impl;

import br.com.fatecmogidascruzes.dao.VeterinarioDAO;
import br.com.fatecmogidascruzes.entities.Veterinario;
import br.com.fatecmogidascruzes.exceptions.DadoDuplicadoException;
import br.com.fatecmogidascruzes.factories.ConnectionFactorySingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class VeterinarioDAOImpl implements VeterinarioDAO {

    private static final Logger logger = LoggerFactory.getLogger(VeterinarioDAOImpl.class);
    private final ConnectionFactorySingleton connectionFactory;

    public VeterinarioDAOImpl(ConnectionFactorySingleton connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void add(Veterinario veterinario) throws SQLException {
        String sql = "INSERT INTO Veterinarios (nome, crmv, especialidade) VALUES (?, ?, ?)";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, veterinario.getNome());
            stmt.setString(2, veterinario.getCrmv());
            stmt.setString(3, veterinario.getEspecialidade());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                logger.error("Falha ao criar Veterinário, nenhuma linha afetada para: {}", veterinario.getCrmv());
                throw new SQLException("Falha ao criar Veterinário, nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    veterinario.setId(generatedKeys.getInt(1));
                } else {
                    logger.error("Falha ao criar Veterinário, nenhum ID gerado para: {}", veterinario.getCrmv());
                    throw new SQLException("Falha ao criar Veterinário, nenhum ID obtido.");
                }
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                logger.warn("Tentativa de cadastrar Veterinário com CRMV duplicado: {}. SQLState: {}. Mensagem: {}",
                        veterinario.getCrmv(), e.getSQLState(), e.getMessage());
                throw new DadoDuplicadoException("Já existe um veterinário cadastrado com este CRMV.", e);
            } else {
                logger.error("Erro SQL inesperado ao adicionar veterinário {}: SQLState: {} - Mensagem: {}",
                        veterinario.getCrmv(), e.getSQLState(), e.getMessage(), e);
                throw e;
            }
        }
    }

    @Override
    public void update(Veterinario veterinario) throws SQLException {
        String sql = "UPDATE Veterinarios SET nome = ?, crmv = ?, especialidade = ? WHERE id = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, veterinario.getNome());
            stmt.setString(2, veterinario.getCrmv());
            stmt.setString(3, veterinario.getEspecialidade());
            stmt.setInt(4, veterinario.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                logger.warn("Tentativa de atualizar Veterinário ID {} sem encontrar registro.", veterinario.getId());
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                logger.warn("Tentativa de atualizar Veterinário com CRMV duplicado: {}. SQLState: {}. Mensagem: {}",
                        veterinario.getCrmv(), e.getSQLState(), e.getMessage());
                throw new DadoDuplicadoException("O CRMV '" + veterinario.getCrmv() + "' já está em uso por outro veterinário.", e);
            } else {
                logger.error("Erro SQL inesperado ao atualizar veterinário {}: SQLState: {} - Mensagem: {}",
                        veterinario.getId(), e.getSQLState(), e.getMessage(), e);
                throw e;
            }
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Veterinarios WHERE id = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Veterinario findById(int id) throws SQLException {
        String sql = "SELECT id, nome, crmv, especialidade FROM Veterinarios WHERE id = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Veterinario(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("crmv"),
                            rs.getString("especialidade")
                    );
                }
            }
        }
        return null;
    }

    @Override
    public List<Veterinario> findAll() throws SQLException {
        List<Veterinario> veterinarios = new ArrayList<>();
        String sql = "SELECT id, nome, crmv, especialidade FROM Veterinarios";
        try (Connection conn = connectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                veterinarios.add(new Veterinario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("crmv"),
                        rs.getString("especialidade")
                ));
            }
        }
        return veterinarios;
    }

    @Override
    public Veterinario findByCrmv(String crmv) throws SQLException {
        String sql = "SELECT id, nome, crmv, especialidade FROM Veterinarios WHERE crmv = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, crmv);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Veterinario(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("crmv"),
                            rs.getString("especialidade")
                    );
                }
            }
        }
        return null;
    }
}