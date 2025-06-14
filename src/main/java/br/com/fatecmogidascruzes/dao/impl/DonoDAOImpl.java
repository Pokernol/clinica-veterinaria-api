package br.com.fatecmogidascruzes.dao.impl;

import br.com.fatecmogidascruzes.dao.DonoDAO;
import br.com.fatecmogidascruzes.entities.Dono;
import br.com.fatecmogidascruzes.factories.ConnectionFactorySingleton;
import br.com.fatecmogidascruzes.exceptions.DadoDuplicadoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DonoDAOImpl implements DonoDAO {

    private static final Logger logger = LoggerFactory.getLogger(DonoDAOImpl.class);
    private final ConnectionFactorySingleton connectionFactory;

    public DonoDAOImpl(ConnectionFactorySingleton connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void add(Dono dono) throws SQLException {
        String sql = "INSERT INTO Donos (nome, telefone, email) VALUES (?, ?, ?)";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, dono.getNome());
            stmt.setString(2, dono.getTelefone());
            stmt.setString(3, dono.getEmail());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                logger.error("Falha ao criar Dono, nenhuma linha afetada para: {}", dono.getEmail());
                throw new SQLException("Falha ao criar Dono, nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    dono.setId(generatedKeys.getInt(1));
                } else {
                    logger.error("Falha ao criar Dono, nenhum ID obtido para: {}", dono.getEmail());
                    throw new SQLException("Falha ao criar Dono, nenhum ID obtido.");
                }
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                String duplicateField = "dados";
                if (e.getMessage().contains("EMAIL")) {
                    duplicateField = "e-mail";
                } else if (e.getMessage().contains("TELEFONE")) {
                    duplicateField = "telefone";
                }
                logger.warn("Tentativa de cadastrar Dono com {} duplicado: {}. SQLState: {}. Mensagem: {}",
                        duplicateField, (duplicateField.equals("e-mail") ? dono.getEmail() : dono.getTelefone()),
                        e.getSQLState(), e.getMessage());
                throw new DadoDuplicadoException("Já existe um dono cadastrado com este " + duplicateField + ".", e);
            } else {
                logger.error("Erro SQL inesperado ao adicionar dono {}: SQLState: {} - Mensagem: {}",
                        dono.getEmail(), e.getSQLState(), e.getMessage(), e);
                throw e;
            }
        }
    }

    @Override
    public void update(Dono dono) throws SQLException {
        String sql = "UPDATE Donos SET nome = ?, telefone = ?, email = ? WHERE id = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, dono.getNome());
            stmt.setString(2, dono.getTelefone());
            stmt.setString(3, dono.getEmail());
            stmt.setInt(4, dono.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                logger.warn("Tentativa de atualizar Dono ID {} sem encontrar registro.", dono.getId());
            }

        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                String duplicateField = "dados";
                if (e.getMessage().contains("EMAIL")) {
                    duplicateField = "e-mail";
                } else if (e.getMessage().contains("TELEFONE")) {
                    duplicateField = "telefone";
                }
                logger.warn("Tentativa de atualizar Dono ID {} com {} duplicado: {}. SQLState: {}. Mensagem: {}",
                        dono.getId(), duplicateField,
                        (duplicateField.equals("e-mail") ? dono.getEmail() : dono.getTelefone()),
                        e.getSQLState(), e.getMessage());
                throw new DadoDuplicadoException("O " + duplicateField + " fornecido já está em uso por outro dono.", e);
            } else {
                logger.error("Erro SQL inesperado ao atualizar dono ID {}: SQLState: {} - Mensagem: {}",
                        dono.getId(), e.getSQLState(), e.getMessage(), e);
                throw e;
            }
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Donos WHERE id = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                logger.warn("Tentativa de excluir Dono ID {} sem encontrar registro.", id);
            }
        } catch (SQLException e) {
            logger.error("Erro SQL inesperado ao excluir dono ID {}: SQLState: {} - Mensagem: {}",
                    id, e.getSQLState(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Dono findById(int id) throws SQLException {
        String sql = "SELECT id, nome, telefone, email FROM Donos WHERE id = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Dono(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("telefone"),
                            rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            logger.error("Erro SQL inesperado ao buscar dono por ID {}: SQLState: {} - Mensagem: {}",
                    id, e.getSQLState(), e.getMessage(), e);
            throw e;
        }
        return null;
    }

    @Override
    public List<Dono> findAll() throws SQLException {
        List<Dono> donos = new ArrayList<>();
        String sql = "SELECT id, nome, telefone, email FROM Donos";
        try (Connection conn = connectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                donos.add(new Dono(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            logger.error("Erro SQL inesperado ao buscar todos os donos: SQLState: {} - Mensagem: {}",
                    e.getSQLState(), e.getMessage(), e);
            throw e;
        }
        return donos;
    }

    @Override
    public Dono findByEmail(String email) throws SQLException {
        String sql = "SELECT id, nome, telefone, email FROM Donos WHERE email = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Dono(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("telefone"),
                            rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            logger.error("Erro SQL inesperado ao buscar dono por email {}: SQLState: {} - Mensagem: {}",
                    email, e.getSQLState(), e.getMessage(), e);
            throw e;
        }
        return null;
    }
}