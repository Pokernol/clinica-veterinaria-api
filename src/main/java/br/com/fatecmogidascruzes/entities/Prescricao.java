package br.com.fatecmogidascruzes.entities;

public class Prescricao {
    private int id;
    private int petId;
    private int veterinarioId;
    private int consultaId;
    private String medicamento;
    private String dosagem;
    private String instrucoesUso;

    public Prescricao(int petId, int veterinarioId, int consultaId, String medicamento, String dosagem, String instrucoesUso) {
        this.petId = petId;
        this.veterinarioId = veterinarioId;
        this.consultaId = consultaId;
        this.medicamento = medicamento;
        this.dosagem = dosagem;
        this.instrucoesUso = instrucoesUso;
    }

    public Prescricao(int id, int petId, int veterinarioId, int consultaId, String medicamento, String dosagem, String instrucoesUso) {
        this.id = id;
        this.petId = petId;
        this.veterinarioId = veterinarioId;
        this.consultaId = consultaId;
        this.medicamento = medicamento;
        this.dosagem = dosagem;
        this.instrucoesUso = instrucoesUso;
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

    public int getConsultaId() {
        return consultaId;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public String getDosagem() {
        return dosagem;
    }

    public String getInstrucoesUso() {
        return instrucoesUso;
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

    public void setConsultaId(int consultaId) {
        this.consultaId = consultaId;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    public void setInstrucoesUso(String instrucoesUso) {
        this.instrucoesUso = instrucoesUso;
    }

    @Override
    public String toString() {
        return "Prescricao{" +
                "\n   id=" + id +
                ",\n    petId=" + petId +
                ",\n    veterinarioId=" + veterinarioId +
                ",\n    consultaId=" + consultaId +
                ",\n    medicamento='" + medicamento + '\'' +
                ",\n    dosagem='" + dosagem + '\'' +
                ",\n    instrucoesUso='" + instrucoesUso + '\'' +
                '}';
    }
}