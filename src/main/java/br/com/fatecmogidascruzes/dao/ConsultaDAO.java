package br.com.fatecmogidascruzes.dao;

import br.com.fatecmogidascruzes.entities.Consulta;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public interface ConsultaDAO extends DAO<Consulta> {
    List<Consulta> findByPetId(int petId) throws SQLException;
    List<Consulta> findByVeterinarioId(int veterinarioId) throws SQLException;
    List<Consulta> findByDataHora(LocalDateTime dataHora) throws SQLException;
    List<Consulta> findByPrescricaoId(int prescricaoId) throws SQLException;
}