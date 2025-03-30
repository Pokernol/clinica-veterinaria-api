package br.com.fatecmogidascruzes.interacoes;

import br.com.fatecmogidascruzes.entities.Consulta;
import br.com.fatecmogidascruzes.entities.Pet;
import java.util.List;
import java.util.Scanner;

public class CadastroConsulta {
    public static Consulta cadastrarConsulta(List<Pet> pets) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Escolha o pet para agendar a consulta:");
        EscolherPet escolherPet = new EscolherPet();
        Pet petEscolhido = escolherPet.escolher(pets);

        if (petEscolhido == null) {
            return null;
        }

        System.out.println("Digite a data da consulta (ex: 01/04/2025):");
        String data = scanner.nextLine();

        System.out.println("Digite o nome do veterinário:");
        String veterinario = scanner.nextLine();

        Consulta novaConsulta = new Consulta(petEscolhido, data, veterinario);

        System.out.println("\nConsulta agendada com sucesso para " + petEscolhido.getNome() + " com o veterinário " + veterinario + " na data " + data);

        return novaConsulta;
    }
}
