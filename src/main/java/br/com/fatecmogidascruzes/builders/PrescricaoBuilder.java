package br.com.fatecmogidascruzes.builders;

import br.com.fatecmogidascruzes.entities.Pet;
import br.com.fatecmogidascruzes.entities.Prescricao;

public class PrescricaoBuilder extends BaseBuilder<Prescricao> {
    private Pet pet;
    private String medicamento;
    private String dosagem;

    public PrescricaoBuilder withPet(Pet pet) {
        this.pet = pet;
        return this;
    }

    public PrescricaoBuilder withMedicamento(String medicamento) {
        this.medicamento = medicamento;
        return this;
    }

    public PrescricaoBuilder withDosagem(String dosagem) {
        this.dosagem = dosagem;
        return this;
    }

    @Override
    public Prescricao build() {
        return new Prescricao(pet, medicamento, dosagem);
    }
}
