package br.com.fatecmogidascruzes.entities;

public class Veterinario {
    private int id;
    private String nome;
    private String crmv;
    private String especialidade;

    public Veterinario(String nome, String crmv, String especialidade) {
        this.nome = nome;
        this.crmv = crmv;
        this.especialidade = especialidade;
    }

    public Veterinario(int id, String nome, String crmv, String especialidade) {
        this.id = id;
        this.nome = nome;
        this.crmv = crmv;
        this.especialidade = especialidade;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCrmv() {
        return crmv;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setId(int id) {
        if (this.id == 0) {
            this.id = id;
        }
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCrmv(String crmv) {
        this.crmv = crmv;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    @Override
    public String toString() {
        return "Veterinario{" +
                "\n    id=" + id +
                ",\n    nome='" + nome + '\'' +
                ",\n    crmv='" + crmv + '\'' +
                ",\n    especialidade='" + especialidade + '\'' +
                "\n}";
    }
}