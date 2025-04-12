package br.com.fatecmogidascruzes.factories;

import br.com.fatecmogidascruzes.builders.ConsultaBuilder;
import br.com.fatecmogidascruzes.entities.Consulta;
import br.com.fatecmogidascruzes.entities.Pet;

public class ConsultaFactory extends BaseFactory<Consulta> {

    @Override
    public Consulta create() {
        return new ConsultaBuilder()
                .withPet(null)
                .withData(null)
                .withVeterinario(null)
                .withObservacoes(null)
                .build();
    }

    public static Consulta create(Pet pet, String data, String veterinario, String observacoes) {
        return new ConsultaBuilder()
                .withPet(pet)
                .withData(data)
                .withVeterinario(veterinario)
                .build();
    }
}