package br.com.fatecmogidascruzes.builders;

import br.com.fatecmogidascruzes.entities.Consulta;
import java.time.LocalDateTime;

public class ConsultaBuilder {
    private int id;
    private int petId;
    private int veterinarioId;
    private LocalDateTime dataHora;
    private String motivoConsulta;
    private int diagnosticoId;
    private int prescricaoId;

    public ConsultaBuilder withId(int id) {
        this.id = id;
        return this;
    }

    public ConsultaBuilder withPetId(int petId) {
        this.petId = petId;
        return this;
    }

    public ConsultaBuilder withVeterinarioId(int veterinarioId) {
        this.veterinarioId = veterinarioId;
        return this;
    }

    public ConsultaBuilder withDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
        return this;
    }

    public ConsultaBuilder withMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
        return this;
    }

    public ConsultaBuilder withDiagnosticoId(int diagnosticoId) {
        this.diagnosticoId = diagnosticoId;
        return this;
    }

    public ConsultaBuilder withPrescricaoId(int prescricaoId) {
        this.prescricaoId = prescricaoId;
        return this;
    }

    public Consulta build() {
        if (this.id == 0) {
            return new Consulta(petId, veterinarioId, dataHora, motivoConsulta);
        } else {
            return new Consulta(id, petId, veterinarioId, dataHora, motivoConsulta, diagnosticoId, prescricaoId);
        }
    }
}