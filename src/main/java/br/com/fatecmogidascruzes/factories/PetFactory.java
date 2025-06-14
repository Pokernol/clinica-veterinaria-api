package br.com.fatecmogidascruzes.factories;

import br.com.fatecmogidascruzes.builders.PetBuilder;
import br.com.fatecmogidascruzes.entities.Pet;
import java.time.LocalDate;

public class PetFactory {
    public static Pet criarPet(String nome, String especie, String raca, LocalDate dataNascimento, int donoId) {
        return new PetBuilder()
                .withNome(nome)
                .withEspecie(especie)
                .withRaca(raca)
                .withDataNascimento(dataNascimento)
                .withDonoId(donoId)
                .build();
    }
}