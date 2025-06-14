package br.com.fatecmogidascruzes.builders;

import br.com.fatecmogidascruzes.entities.Prescricao;

public class PrescricaoBuilder {
    private int id;
    private int petId;
    private int veterinarioId;
    private int consultaId;
    private String medicamento;
    private String dosagem;
    private String instrucoesUso;

    public PrescricaoBuilder withId(int id) {
        this.id = id;
        return this;
    }

    public PrescricaoBuilder withPetId(int petId) {
        this.petId = petId;
        return this;
    }

    public PrescricaoBuilder withVeterinarioId(int veterinarioId) {
        this.veterinarioId = veterinarioId;
        return this;
    }

    public PrescricaoBuilder withConsultaId(int consultaId) {
        this.consultaId = consultaId;
        return this;
    }

    public PrescricaoBuilder withMedicamento(String medicamento) {
        this.medicamento = medicamento;
        return this;
    }

    public PrescricaoBuilder withDosagem(String dosagem) {
        this.dosagem = dosagem;
        return this;
    }

    public PrescricaoBuilder withInstrucoesUso(String instrucoesUso) {
        this.instrucoesUso = instrucoesUso;
        return this;
    }

    public Prescricao build() {
        if (this.id == 0) {
            return new Prescricao(petId, veterinarioId, consultaId, medicamento, dosagem, instrucoesUso);
        } else {
            return new Prescricao(id, petId, veterinarioId, consultaId, medicamento, dosagem, instrucoesUso);
        }
    }
}