package br.com.fatecmogidascruzes.entities;

public class Diagnostico {
    private int id;
    private int petId;
    private int consultaId;
    private String descricao;
    private String gravidade;

    public Diagnostico(int petId, int consultaId, String descricao, String gravidade) {
        this.petId = petId;
        this.consultaId = consultaId;
        this.descricao = descricao;
        this.gravidade = gravidade;
    }

    public Diagnostico(int id, int petId, int consultaId, String descricao, String gravidade) {
        this.id = id;
        this.petId = petId;
        this.consultaId = consultaId;
        this.descricao = descricao;
        this.gravidade = gravidade;
    }

    public int getId() {
        return id;
    }

    public int getPetId() {
        return petId;
    }

    public int getConsultaId() {
        return consultaId;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getGravidade() {
        return gravidade;
    }

    public void setId(int id) {
        if (this.id == 0) {
            this.id = id;
        }
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public void setConsultaId(int consultaId) {
        this.consultaId = consultaId;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setGravidade(String gravidade) {
        this.gravidade = gravidade;
    }

    @Override
    public String toString() {
        return "Diagnostico{" +
                "\n   id=" + id +
                ",\n    petId=" + petId +
                ",\n    consultaId=" + consultaId +
                ",\n    descricao='" + descricao + '\'' +
                ",\n    gravidade='" + gravidade + '\'' +
                "\n}";
    }
}