package br.com.fatecmogidascruzes.dao;

import br.com.fatecmogidascruzes.entities.Consulta;
import java.sql.SQLException;
import java.util.List;

public interface ConsultaDAO extends DAO<Consulta> {
    List<Consulta> findByPetId(int petId) throws SQLException;
    List<Consulta> findByVeterinarioId(int veterinarioId) throws SQLException;
}