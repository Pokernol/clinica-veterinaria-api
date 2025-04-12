package br.com.fatecmogidascruzes.entities;

public class Diagnostico implements ServicoVeterinario {
    private Pet pet;
    private String descricao;

    public Diagnostico(Pet pet, String descricao) {
        this.pet = pet;
        this.descricao = descricao;
    }

    public Pet getPet() {
        return pet;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public void realizarServico() {
        System.out.println("Diagnóstico para " + pet.getNome() + ": " + descricao);
    }

    @Override
    public String toString() {
        return "Diagnostico{" +
                "pet=" + pet.getNome() +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
