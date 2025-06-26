package br.com.fatecmogidascruzes.factories;

import br.com.fatecmogidascruzes.builders.DiagnosticoBuilder;
import br.com.fatecmogidascruzes.entities.Diagnostico;

public class DiagnosticoFactory {
    public static Diagnostico criarDiagnostico(int petId, int consultaId, String descricao, String gravidade) {
        return new DiagnosticoBuilder()
                .withPetId(petId)
                .withConsultaId(consultaId)
                .withDescricao(descricao)
                .withGravidade(gravidade)
                .build();
    }
}