package br.com.fatecmogidascruzes.interacoes;

import br.com.fatecmogidascruzes.entities.Diagnostico;
import br.com.fatecmogidascruzes.entities.Pet;
import java.util.List;
import java.util.Scanner;

public class CadastroDiagnostico {
    public static Diagnostico cadastrarDiagnostico(List<Pet> pets) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Escolha o pet para Diagnostico da consulta:");
        EscolherPet escolherPet = new EscolherPet();
        Pet petEscolhido = escolherPet.escolher(pets);

        if (petEscolhido == null) {
            return null;
        }

        System.out.println("Digite o diagnóstico para o pet " + petEscolhido.getNome() + ":");
        String descricao = scanner.nextLine();

        Diagnostico novoDiagnostico = new Diagnostico(petEscolhido, descricao);

        System.out.println("\nDiagnóstico registrado com sucesso para " + petEscolhido.getNome() + ": " + descricao);

        return novoDiagnostico;
    }
}
