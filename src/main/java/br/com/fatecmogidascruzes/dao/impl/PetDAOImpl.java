package br.com.fatecmogidascruzes.dao.impl;

import br.com.fatecmogidascruzes.dao.PetDAO;
import br.com.fatecmogidascruzes.entities.Pet;
import br.com.fatecmogidascruzes.factories.ConnectionFactorySingleton;
import br.com.fatecmogidascruzes.builders.PetBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PetDAOImpl implements PetDAO {

    private static final Logger logger = LoggerFactory.getLogger(PetDAOImpl.class);
    private final ConnectionFactorySingleton connectionFactory;

    public PetDAOImpl(ConnectionFactorySingleton connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void add(Pet pet) throws SQLException {
        String sql = "INSERT INTO Pets (nome, especie, raca, dataNascimento, donoId) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, pet.getNome());
            stmt.setString(2, pet.getEspecie());
            stmt.setString(3, pet.getRaca());
            stmt.setDate(4, Date.valueOf(pet.getDataNascimento()));
            stmt.setInt(5, pet.getDonoId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                logger.error("Falha ao criar Pet, nenhuma linha afetada para: {}", pet.getNome());
                throw new SQLException("Falha ao criar Pet, nenhuma linha afetada.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pet.setId(generatedKeys.getInt(1));
                } else {
                    logger.error("Falha ao criar Pet, nenhum ID obtido para: {}", pet.getNome());
                    throw new SQLException("Falha ao criar Pet, nenhum ID obtido.");
                }
            }
        } catch (SQLException e) {
            logger.error("Erro SQL inesperado ao adicionar pet {}: SQLState: {} - Mensagem: {}",
                    pet.getNome(), e.getSQLState(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void update(Pet pet) throws SQLException {
        String sql = "UPDATE Pets SET nome = ?, especie = ?, raca = ?, dataNascimento = ?, donoId = ? WHERE id = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pet.getNome());
            stmt.setString(2, pet.getEspecie());
            stmt.setString(3, pet.getRaca());
            stmt.setDate(4, Date.valueOf(pet.getDataNascimento()));
            stmt.setInt(5, pet.getDonoId());
            stmt.setInt(6, pet.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                logger.warn("Tentativa de atualizar Pet ID {} sem encontrar registro.", pet.getId());
            }
        } catch (SQLException e) {
            logger.error("Erro SQL inesperado ao atualizar pet ID {}: SQLState: {} - Mensagem: {}",
                    pet.getId(), e.getSQLState(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Pets WHERE id = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                logger.warn("Tentativa de excluir Pet ID {} sem encontrar registro.", id);
            }
        } catch (SQLException e) {
            logger.error("Erro SQL inesperado ao excluir pet ID {}: SQLState: {} - Mensagem: {}",
                    id, e.getSQLState(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Pet findById(int id) throws SQLException {
        String sql = "SELECT id, nome, especie, raca, dataNascimento, donoId FROM Pets WHERE id = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new PetBuilder()
                            .withId(rs.getInt("id"))
                            .withNome(rs.getString("nome"))
                            .withEspecie(rs.getString("especie"))
                            .withRaca(rs.getString("raca"))
                            .withDataNascimento(rs.getDate("dataNascimento").toLocalDate())
                            .withDonoId(rs.getInt("donoId"))
                            .build();
                }
            }
        } catch (SQLException e) {
            logger.error("Erro SQL inesperado ao buscar pet por ID {}: SQLState: {} - Mensagem: {}",
                    id, e.getSQLState(), e.getMessage(), e);
            throw e;
        }
        return null;
    }

    @Override
    public List<Pet> findAll() throws SQLException {
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT id, nome, especie, raca, dataNascimento, donoId FROM Pets";
        try (Connection conn = connectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                pets.add(new PetBuilder()
                        .withId(rs.getInt("id"))
                        .withNome(rs.getString("nome"))
                        .withEspecie(rs.getString("especie"))
                        .withRaca(rs.getString("raca"))
                        .withDataNascimento(rs.getDate("dataNascimento").toLocalDate())
                        .withDonoId(rs.getInt("donoId"))
                        .build());
            }
        } catch (SQLException e) {
            logger.error("Erro SQL inesperado ao buscar todos os pets: SQLState: {} - Mensagem: {}",
                    e.getSQLState(), e.getMessage(), e);
            throw e;
        }
        return pets;
    }

    @Override
    public List<Pet> findByDonoId(int donoId) throws SQLException {
        List<Pet> pets = new ArrayList<>();
        String sql = "SELECT id, nome, especie, raca, dataNascimento, donoId FROM Pets WHERE donoId = ?";
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, donoId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pets.add(new PetBuilder()
                            .withId(rs.getInt("id"))
                            .withNome(rs.getString("nome"))
                            .withEspecie(rs.getString("especie"))
                            .withRaca(rs.getString("raca"))
                            .withDataNascimento(rs.getDate("dataNascimento").toLocalDate())
                            .withDonoId(rs.getInt("donoId"))
                            .build());
                }
            }
        } catch (SQLException e) {
            logger.error("Erro SQL inesperado ao buscar pets por Dono ID {}: SQLState: {} - Mensagem: {}",
                    donoId, e.getSQLState(), e.getMessage(), e);
            throw e;
        }
        return pets;
    }
}