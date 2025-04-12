package br.com.fatecmogidascruzes.entities;

public class Prescricao implements ServicoVeterinario {
    private Pet pet;
    private String medicamento;
    private String dosagem;

    public Prescricao(Pet pet, String medicamento, String dosagem) {
        this.pet = pet;
        this.medicamento = medicamento;
        this.dosagem = dosagem;
    }

    public Pet getPet() {
        return pet;
    }

    public String getMedicamento() {
        return medicamento;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public void setMedicamento(String medicamento) {
        this.medicamento = medicamento;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    @Override
    public void realizarServico() {
        System.out.println("Prescrição para " + pet.getNome() + ": " + medicamento + " - " + dosagem);
    }

    @Override
    public String toString() {
        return "Prescricao{" +
                "pet=" + pet.getNome() +
                ", medicamento='" + medicamento + '\'' +
                ", dosagem='" + dosagem + '\'' +
                '}';
    }
}