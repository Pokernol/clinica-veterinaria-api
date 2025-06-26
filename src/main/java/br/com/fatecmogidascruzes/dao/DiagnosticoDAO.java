package br.com.fatecmogidascruzes.dao;

import br.com.fatecmogidascruzes.entities.Diagnostico;
import java.sql.SQLException;
import java.util.List;

public interface DiagnosticoDAO extends DAO<Diagnostico> {
    List<Diagnostico> findByPetId(int petId) throws SQLException;
    List<Diagnostico> findByConsultaId(int consultaId) throws SQLException;
}