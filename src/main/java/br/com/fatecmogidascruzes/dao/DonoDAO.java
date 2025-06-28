package br.com.fatecmogidascruzes.dao;

import br.com.fatecmogidascruzes.entities.Dono;
import java.sql.SQLException;

public interface DonoDAO extends DAO<Dono> {
    Dono findByEmail(String email) throws SQLException;
    Dono findByPetId(int petId) throws SQLException;
}