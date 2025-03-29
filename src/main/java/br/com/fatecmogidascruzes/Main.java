package br.com.fatecmogidascruzes;

import br.com.fatecmogidascruzes.entities.*;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Pet pet1 = new Pet("Rex", "Cachorro", "João");
        Pet pet2 = new Pet("Mia", "Gato", "Maria");

        List<ServicoVeterinario> servicos = new ArrayList<>();
        servicos.add(new Consulta(pet1, "01/04/2025", "Dr. Pedro"));
        servicos.add(new Diagnostico(pet2, "Infecção respiratória"));
        servicos.add(new Prescricao(pet2, "Antibiótico", "2x ao dia"));

        for (ServicoVeterinario servico : servicos) {
            servico.realizarServico();
        }
    }
}