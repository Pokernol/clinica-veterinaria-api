package br.com.fatecmogidascruzes.entities;

public class Consulta implements ServicoVeterinario {
    private Pet pet;
    private String data;
    private String veterinario;
    private String observacoes;

    public Consulta(Pet pet, String data, String veterinario, String observacoes) {
        this.pet = pet;
        this.data = data;
        this.veterinario = veterinario;
        this.observacoes = observacoes;
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

    public String getObservacoes() {
        return observacoes;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setVeterinario(String veterinario) {
        this.veterinario = veterinario;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    @Override
    public void realizarServico() {
        System.out.println("Consulta realizada para " + pet.getNome() + 
                " com o veterinário " + getVeterinario() + " na data " + getData() +
                ". Observações: " + getObservacoes());
    }

    @Override
    public String toString() {
        return "Consulta{" +
                "pet=" + pet +
                ", data='" + data + '\'' +
                ", veterinario='" + veterinario + '\'' +
                ", observacoes='" + observacoes + '\'' +
                '}';
    }
}
