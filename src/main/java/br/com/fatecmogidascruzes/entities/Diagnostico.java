package br.com.fatecmogidascruzes.entities;

public class Diagnostico implements ServicoVeterinario {
    private Pet pet;
    private String descricao;
    private String gravidade;

    public Diagnostico(Pet pet, String descricao, String gravidade) {
        this.pet = pet;
        this.descricao = descricao;
        this.gravidade = gravidade;
    }

    public Pet getPet() {
        return pet;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getGravidade() {
        return gravidade;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setGravidade(String gravidade) {
        this.gravidade = gravidade;
    }

    @Override
    public void realizarServico() {
        System.out.println("Diagnóstico para " + pet.getNome() + ": " + descricao +
                " Gravidade: " + gravidade);
    }

    @Override
    public String toString() {
        return "Diagnostico{" +
                "pet=" + pet.getNome() +
                ", descricao='" + descricao + '\'' +
                ", gravidade='" + gravidade + '\'' +
                '}';
    }
}
