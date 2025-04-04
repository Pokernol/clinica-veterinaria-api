package br.com.fatecmogidascruzes.entities;

public class Diagnostico implements ServicoVeterinario {
    private Pet pet;
    private String descricao;

    public Diagnostico(Pet pet, String descricao) {
        this.pet = pet;
        this.descricao = descricao;
    }

    @Override
    public void realizarServico() {
        System.out.println("Diagnóstico para " + pet.getNome() + ": " + descricao);
    }
}
