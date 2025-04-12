package br.com.fatecmogidascruzes.factories;

import br.com.fatecmogidascruzes.entities.Pet;
import br.com.fatecmogidascruzes.builders.PetBuilder;

public class PetFactory extends BaseFactory<Pet> {

    @Override
    public Pet create() {
        return new PetBuilder()
                .withNome(null)
                .withEspecie(null)
                .withDono(null)
                .withIdade(0)
                .build();
    }

    public static Pet create(String nome, String especie, String dono,int idade) {
        return new PetBuilder()
                .withNome(nome)
                .withEspecie(especie)
                .withDono(dono)
                .withIdade(idade)
                .build();
    }
}
