package br.com.fatecmogidascruzes.entities;

import java.time.LocalDateTime; // Importar java.time

public class Consulta implements ServicoVeterinario {
    private int id;
    private int petId;
    private int veterinarioId;
    private LocalDateTime dataHora;
    private String motivoConsulta;
    private String diagnostico;
    private int prescricaoId;

    public Consulta(int petId, int veterinarioId, LocalDateTime dataHora, String motivoConsulta) {
        this.petId = petId;
        this.veterinarioId = veterinarioId;
        this.dataHora = dataHora;
        this.motivoConsulta = motivoConsulta;
        this.diagnostico = null;
        this.prescricaoId = 0;
    }

    public Consulta(int id, int petId, int veterinarioId, LocalDateTime dataHora, String motivoConsulta, String diagnostico, int prescricaoId) {
        this.id = id;
        this.petId = petId;
        this.veterinarioId = veterinarioId;
        this.dataHora = dataHora;
        this.motivoConsulta = motivoConsulta;
        this.diagnostico = diagnostico;
        this.prescricaoId = prescricaoId;
    }

    public int getId() {
        return id;
    }

    public int getPetId() {
        return petId;
    }

    public int getVeterinarioId() {
        return veterinarioId;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public String getMotivoConsulta() {
        return motivoConsulta;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public int getPrescricaoId() {
        return prescricaoId;
    }

    public void setId(int id) {
        if (this.id == 0) {
            this.id = id;
        }
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public void setVeterinarioId(int veterinarioId) {
        this.veterinarioId = veterinarioId;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public void setMotivoConsulta(String motivoConsulta) {
        this.motivoConsulta = motivoConsulta;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public void setPrescricaoId(int prescricaoId) {
        this.prescricaoId = prescricaoId;
    }

    @Override
    public void realizarServico() {
        System.out.println("Consulta agendada para Pet ID: " + petId +
                " com Veterinário ID: " + veterinarioId + " em " + dataHora +
                ". Motivo: " + motivoConsulta);
        if (diagnostico != null && !diagnostico.isEmpty()) {
            System.out.println("  Diagnóstico: " + diagnostico);
        }
        if (prescricaoId != 0) {
            System.out.println("  Associada à Prescrição ID: " + prescricaoId);
        }
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "\n   id=" + id +
                ",\n    petId=" + petId +
                ",\n    veterinarioId=" + veterinarioId +
                ",\n    dataHora=" + dataHora +
                ",\n    motivoConsulta='" + motivoConsulta + '\'' +
                ",\n    diagnostico='" + diagnostico + '\'' +
                ",\n    prescricaoId=" + prescricaoId +
                "\n}";
    }
}