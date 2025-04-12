package br.com.fatecmogidascruzes.builders;

import br.com.fatecmogidascruzes.entities.Pet;

public class PetBuilder extends BaseBuilder<Pet> {
    private String nome;
    private String especie;
    private String dono;
    private int idade;

    public PetBuilder withNome(String nome) {
        this.nome = nome;
        return this;
    }

    public PetBuilder withEspecie(String especie) {
        this.especie = especie;
        return this;
    }

    public PetBuilder withDono(String dono) {
        this.dono = dono;
        return this;
    }

    public PetBuilder withIdade(int idade) {
        this.idade = idade;
        return this;
    }

    @Override
    public Pet build() {
        return new Pet(nome, especie, dono, idade);
    }
}