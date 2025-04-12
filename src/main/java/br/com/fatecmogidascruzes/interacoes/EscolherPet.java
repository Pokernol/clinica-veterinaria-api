package br.com.fatecmogidascruzes.interacoes;

import br.com.fatecmogidascruzes.entities.Pet;
import java.util.List;
import java.util.Scanner;

public class EscolherPet {
    private final Scanner scanner;

    public EscolherPet() {
        this.scanner = new Scanner(System.in);
    }

    public Pet escolher(List<Pet> pets) {
        for (int i = 0; i < pets.size(); i++) {
            System.out.println((i + 1) + " - " + pets.get(i).getNome() + " (Tipo: " + pets.get(i).getEspecie() + ")");
        }
        int petIndex = scanner.nextInt();
        scanner.nextLine();

        if (petIndex < 1 || petIndex > pets.size()) {
            System.out.println("Opção inválida.");
            return null;
        }

        return pets.get(petIndex - 1);
    }
}
