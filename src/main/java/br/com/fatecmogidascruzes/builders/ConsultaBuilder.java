package br.com.fatecmogidascruzes.builders;

import br.com.fatecmogidascruzes.entities.Consulta;
import br.com.fatecmogidascruzes.entities.Pet;

public class ConsultaBuilder extends BaseBuilder<Consulta> {
    private Pet pet;
    private String data;
    private String veterinario;

    public ConsultaBuilder withPet(Pet pet) {
        this.pet = pet;
        return this;
    }

    public ConsultaBuilder withData(String data) {
        this.data = data;
        return this;
    }

    public ConsultaBuilder withVeterinario(String veterinario) {
        this.veterinario = veterinario;
        return this;
    }

    @Override
    public Consulta build() {
        return new Consulta(pet, data, veterinario);
    }
}
