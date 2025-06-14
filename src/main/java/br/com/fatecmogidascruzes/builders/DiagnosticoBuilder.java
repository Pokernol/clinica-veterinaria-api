package br.com.fatecmogidascruzes.builders;

import br.com.fatecmogidascruzes.entities.Diagnostico;

public class DiagnosticoBuilder {
    private int id;
    private int petId;
    private int consultaId;
    private String descricao;
    private String gravidade;

    public DiagnosticoBuilder withId(int id) {
        this.id = id;
        return this;
    }

    public DiagnosticoBuilder withPetId(int petId) {
        this.petId = petId;
        return this;
    }

    public DiagnosticoBuilder withConsultaId(int consultaId) {
        this.consultaId = consultaId;
        return this;
    }

    public DiagnosticoBuilder withDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public DiagnosticoBuilder withGravidade(String gravidade) {
        this.gravidade = gravidade;
        return this;
    }

    public Diagnostico build() {
        if (this.id == 0) {
            return new Diagnostico(petId, consultaId, descricao, gravidade);
        } else {
            return new Diagnostico(id, petId, consultaId, descricao, gravidade);
        }
    }
}