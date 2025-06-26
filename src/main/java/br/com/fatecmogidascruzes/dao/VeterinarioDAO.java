package br.com.fatecmogidascruzes.dao;

import br.com.fatecmogidascruzes.entities.Veterinario;
import java.sql.SQLException;

public interface VeterinarioDAO extends DAO<Veterinario> {
    Veterinario findByCrmv(String crmv) throws SQLException;
}