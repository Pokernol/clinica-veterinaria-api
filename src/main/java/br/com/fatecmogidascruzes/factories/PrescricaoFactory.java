package br.com.fatecmogidascruzes.factories;

import br.com.fatecmogidascruzes.builders.PrescricaoBuilder;
import br.com.fatecmogidascruzes.entities.Prescricao;

public class PrescricaoFactory {
    public static Prescricao criarPrescricao(int petId, int veterinarioId, int consultaId, String medicamento, String dosagem, String instrucoesUso) {
        return new PrescricaoBuilder()
                .withPetId(petId)
                .withVeterinarioId(veterinarioId)
                .withConsultaId(consultaId)
                .withMedicamento(medicamento)
                .withDosagem(dosagem)
                .withInstrucoesUso(instrucoesUso)
                .build();
    }
}
