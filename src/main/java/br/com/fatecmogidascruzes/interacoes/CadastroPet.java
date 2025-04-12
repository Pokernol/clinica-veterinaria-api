package br.com.fatecmogidascruzes.interacoes;

import br.com.fatecmogidascruzes.entities.Pet;
import br.com.fatecmogidascruzes.factories.PetFactory;

import java.util.Scanner;

public class CadastroPet {
    public static Pet cadastrarPet() {
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.println("\nDigite os dados do novo pet:");

            System.out.println("Digite o nome do pet:");
            String nome = scanner.nextLine();

            System.out.println("Digite o tipo do pet (ex: Cachorro, Gato):");
            String tipo = scanner.nextLine();

            System.out.println("Digite o nome do dono do pet:");
            String dono = scanner.nextLine();

            System.out.println("Digite a idade do pet:");
            int idade = scanner.nextInt();

            Pet novoPet = PetFactory.create(nome, tipo, dono, idade);

            System.out.println("\nCadastro de pet realizado com sucesso!");
            System.out.println(novoPet);
            return novoPet;
        }
    }
}
