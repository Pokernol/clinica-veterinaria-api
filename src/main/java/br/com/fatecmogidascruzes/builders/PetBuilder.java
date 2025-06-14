package br.com.fatecmogidascruzes.builders;

import br.com.fatecmogidascruzes.entities.Pet;
import java.time.LocalDate;

public class PetBuilder {
    private int id;
    private String nome;
    private String especie;
    private String raca;
    private LocalDate dataNascimento;
    private int donoId;

    public PetBuilder withId(int id) {
        this.id = id;
        return this;
    }

    public PetBuilder withNome(String nome) {
        this.nome = nome;
        return this;
    }

    public PetBuilder withEspecie(String especie) {
        this.especie = especie;
        return this;
    }

    public PetBuilder withRaca(String raca) {
        this.raca = raca;
        return this;
    }

    public PetBuilder withDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
        return this;
    }

    public PetBuilder withDonoId(int donoId) {
        this.donoId = donoId;
        return this;
    }

    public Pet build() {
        if (this.id == 0) {
            return new Pet(nome, especie, raca, dataNascimento, donoId);
        } else {
            return new Pet(id, nome, especie, raca, dataNascimento, donoId);
        }
    }
}