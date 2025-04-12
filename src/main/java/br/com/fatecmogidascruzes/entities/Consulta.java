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

    public Pet getPet() {
        return pet;
    }

    public String getData() {
        return data;
    }

    public String getVeterinario() {
        return veterinario;
    }

    @Override
    public void realizarServico() {
        System.out.println("Consulta realizada para " + pet.getNome() + 
                " com o veterinário " + getVeterinario() + " na data " + getData());
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "pet=" + pet +
                ", data='" + data + '\'' +
                ", veterinario='" + veterinario + '\'' +
                '}';
    }
}
