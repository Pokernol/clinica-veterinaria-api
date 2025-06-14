package br.com.fatecmogidascruzes.dao;

import br.com.fatecmogidascruzes.entities.Prescricao;
import java.sql.SQLException;
import java.util.List;

public interface PrescricaoDAO extends DAO<Prescricao> {
    List<Prescricao> findByPetId(int petId) throws SQLException;
    List<Prescricao> findByVeterinarioId(int veterinarioId) throws SQLException;
    List<Prescricao> findByConsultaId(int consultaId) throws SQLException;
}