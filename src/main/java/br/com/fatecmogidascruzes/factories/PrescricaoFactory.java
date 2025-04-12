package br.com.fatecmogidascruzes.factories;

import br.com.fatecmogidascruzes.entities.Pet;
import br.com.fatecmogidascruzes.entities.Prescricao;
import br.com.fatecmogidascruzes.builders.PrescricaoBuilder;

public class PrescricaoFactory extends BaseFactory<Prescricao> {

    @Override
    public Prescricao create() {
        return new PrescricaoBuilder()
                .withPet(null)
                .withMedicamento(null)
                .withDosagem(null)
                .build();
    }

    public static Prescricao create(Pet pet, String medicamento, String dosagem) {
        return new PrescricaoBuilder()
                .withPet(pet)
                .withMedicamento(medicamento)
                .withDosagem(dosagem)
                .build();
    }
}
