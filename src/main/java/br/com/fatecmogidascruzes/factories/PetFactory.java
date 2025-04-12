package br.com.fatecmogidascruzes.factories;

import br.com.fatecmogidascruzes.entities.Pet;
import br.com.fatecmogidascruzes.builders.PetBuilder;

public class PetFactory extends BaseFactory<Pet> {

    @Override
    public Pet create() {
        return new PetBuilder()
                .withNome("Rex")
                .withEspecie("Cachorro")
                .withDono("João Silva")
                .withIdade(3)
                .withRaca("Labrador")
                .build();
    }
}