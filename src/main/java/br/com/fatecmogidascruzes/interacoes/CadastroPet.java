package br.com.fatecmogidascruzes.interacoes;

import br.com.fatecmogidascruzes.entities.Pet;
import java.util.Scanner;

public class CadastroPet {
    public static Pet cadastrarPet() {
        Scanner scanner = new Scanner(System.in);

        // Exibe as opções de cadastro para o usuário
        System.out.println("\nDigite os dados do novo pet:");

        System.out.println("Digite o nome do pet:");
        String nome = scanner.nextLine();

        System.out.println("Digite o tipo do pet (ex: Cachorro, Gato):");
        String tipo = scanner.nextLine();

        System.out.println("Digite o nome do dono do pet:");
        String dono = scanner.nextLine();

        // Cria o novo pet
        Pet novoPet = new Pet(nome, tipo, dono);

        // Confirma o cadastro do pet
        System.out.println("\nCadastro de pet realizado com sucesso!");
        System.out.println(novoPet);

        // Retorna o pet cadastrado
        return novoPet;
    }
}
