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