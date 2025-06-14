package br.com.fatecmogidascruzes.dao;

import br.com.fatecmogidascruzes.entities.Pet;
import java.sql.SQLException;
import java.util.List;

public interface PetDAO extends DAO<Pet> {
    List<Pet> findByDonoId(int donoId) throws SQLException;
}