package br.com.fatecmogidascruzes.adapters;

import br.com.fatecmogidascruzes.dao.PetDAO;
import br.com.fatecmogidascruzes.dtos.DonoComDetalhes;
import br.com.fatecmogidascruzes.entities.Dono;
import br.com.fatecmogidascruzes.entities.Pet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class DonoAdapter {

    private static final Logger logger = LoggerFactory.getLogger(DonoAdapter.class);
    private final PetDAO petDAO;

    public DonoAdapter(PetDAO petDAO) {
        this.petDAO = petDAO;
    }

    /**
     * Adapta um objeto Dono para um DonoComDetalhes, buscando os pets associados.
     * @param dono A entidade Dono a ser adaptada.
     * @return Um objeto DonoComDetalhes com os dados do Dono e a lista de seus Pets.
     */
    public DonoComDetalhes adapt(Dono dono) {
        if (dono == null) {
            return null;
        }

        List<Pet> petsDoDono = Collections.emptyList();
        try {
            petsDoDono = petDAO.findByDonoId(dono.getId());
        } catch (SQLException e) {
            logger.error("Erro ao buscar pets para o dono ID {}: SQLState: {} - Mensagem: {}",
                    dono.getId(), e.getSQLState(), e.getMessage(), e);
        }

        return new DonoComDetalhes(
                dono.getId(),
                dono.getNome(),
                dono.getTelefone(),
                dono.getEmail(),
                petsDoDono
        );
    }

    /**
     * Adapta uma lista de objetos Dono para uma lista de DonoComDetalhes.
     * @param donos A lista de entidades Dono a ser adaptada.
     * @return Uma lista de objetos DonoComDetalhes.
     */
    public List<DonoComDetalhes> adapt(List<Dono> donos) {
        if (donos == null || donos.isEmpty()) {
            return Collections.emptyList();
        }

        return donos.stream()
                .map(this::adapt)
                .collect(java.util.stream.Collectors.toList());
    }
}