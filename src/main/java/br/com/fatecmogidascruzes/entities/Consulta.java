package br.com.fatecmogidascruzes.entities;

public class Consulta implements ServicoVeterinario {
    private Pet pet;
    private String data;
    private String veterinario;

    public Consulta(Pet pet, String data, String veterinario) {
        this.pet = pet;
        this.data = data;
        this.veterinario = veterinario;
    }

    @Override
    public void realizarServico() {
        System.out.println("Consulta realizada para " + pet.getNome() + " com o veterinário " + veterinario + " na data " + data);
    }
}
