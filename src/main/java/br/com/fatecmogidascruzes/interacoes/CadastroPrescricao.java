package br.com.fatecmogidascruzes.interacoes;

import br.com.fatecmogidascruzes.builders.PrescricaoBuilder;
import br.com.fatecmogidascruzes.entities.Pet;
import br.com.fatecmogidascruzes.entities.Prescricao;
import java.util.List;
import java.util.Scanner;

public class CadastroPrescricao {
    public static Prescricao cadastrarPrescricao(List<Pet> pets) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Escolha o pet para prescrição:");
            EscolherPet escolherPet = new EscolherPet();
            Pet petEscolhido = escolherPet.escolher(pets);

            if (petEscolhido == null) {
                return null;
            }

            System.out.println("Digite o nome do medicamento para o pet " + petEscolhido.getNome() + ":");
            String medicamento = scanner.nextLine();

            System.out.println("Digite a dosagem do medicamento para o pet " + petEscolhido.getNome() + ":");
            String dosagem = scanner.nextLine();

            Prescricao novaPrescricao = new PrescricaoBuilder()
                    .withPet(petEscolhido)
                    .withMedicamento(medicamento)
                    .withDosagem(dosagem)
                    .build();

            System.out.println("\nPrescrição registrada com sucesso para " + petEscolhido.getNome() + ": " + medicamento + " - " + dosagem);
            return novaPrescricao;
        }
    }
}
