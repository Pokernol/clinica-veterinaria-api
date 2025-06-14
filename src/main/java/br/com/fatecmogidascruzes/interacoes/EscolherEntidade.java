package br.com.fatecmogidascruzes.interacoes;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class EscolherEntidade {

    public static <T> T escolher(Scanner scanner, List<T> entidades) {
        if (entidades.isEmpty()) {
            System.out.println("Nenhum item disponível para escolha.");
            return null;
        }

        System.out.println("Escolha um item da lista:");
        for (int i = 0; i < entidades.size(); i++) {
            System.out.println((i + 1) + " - " + entidades.get(i).toString());
        }

        int escolha = -1;
        boolean entradaValida = false;
        while (!entradaValida) {
            try {
                System.out.print("Digite o número correspondente: ");
                escolha = scanner.nextInt();
                scanner.nextLine();

                if (escolha > 0 && escolha <= entidades.size()) {
                    entradaValida = true;
                } else {
                    System.out.println("Opção inválida. Por favor, digite um número da lista.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.nextLine();
            }
        }
        return entidades.get(escolha - 1);
    }
}