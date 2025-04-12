package br.com.fatecmogidascruzes.builders;

import br.com.fatecmogidascruzes.entities.Diagnostico;
import br.com.fatecmogidascruzes.entities.Pet;

public class DiagnosticoBuilder extends BaseBuilder<Diagnostico> {
    private Pet pet;
    private String descricao;

    public DiagnosticoBuilder withPet(Pet pet) {
        this.pet = pet;
        return this;
    }

    public DiagnosticoBuilder withDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    @Override
    public Diagnostico build() {
        return new Diagnostico(pet, descricao);
    }
}
