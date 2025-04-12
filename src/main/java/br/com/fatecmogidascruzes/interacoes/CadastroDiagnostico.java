package br.com.fatecmogidascruzes.interacoes;

import br.com.fatecmogidascruzes.entities.Diagnostico;
import br.com.fatecmogidascruzes.entities.Pet;
import br.com.fatecmogidascruzes.factories.DiagnosticoFactory;

import java.util.List;
import java.util.Scanner;

public class CadastroDiagnostico {
    public static Diagnostico cadastrarDiagnostico(List<Pet> pets) {
        try (Scanner scanner = new Scanner(System.in)) {

            System.out.println("Escolha o pet para Diagnostico da consulta:");
            EscolherPet escolherPet = new EscolherPet();
            Pet petEscolhido = escolherPet.escolher(pets);

            if (petEscolhido == null) {
                return null;
            }

            System.out.println("Digite o diagnóstico para o pet " + petEscolhido.getNome() + ":");
            String descricao = scanner.nextLine();
            System.out.println("Digite a gravidade do diagnóstico (ex: Leve, Moderada, Grave):");
            String gravidade = scanner.nextLine();          

            Diagnostico novoDiagnostico = DiagnosticoFactory.create(petEscolhido, descricao, gravidade);

            System.out.println("\nDiagnóstico registrado com sucesso para " + petEscolhido.getNome() + ": " + descricao);

            scanner.close();
            return novoDiagnostico;
        }
    }
}
