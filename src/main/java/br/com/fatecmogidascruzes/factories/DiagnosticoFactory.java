package br.com.fatecmogidascruzes.factories;

import br.com.fatecmogidascruzes.builders.DiagnosticoBuilder;
import br.com.fatecmogidascruzes.entities.Diagnostico;
import br.com.fatecmogidascruzes.entities.Pet;

public class DiagnosticoFactory extends BaseFactory<Diagnostico> {

    @Override
    public Diagnostico create() {
        return new DiagnosticoBuilder()
                .withPet(null)
                .withDescricao(null)
                .withGravidade(null)
                .build();
    }

    public static Diagnostico create(Pet pet, String descricao, String gravidade) {
        return new DiagnosticoBuilder()
                .withPet(pet)
                .withDescricao(descricao)
                .withGravidade(gravidade)
                .build();
    }
}