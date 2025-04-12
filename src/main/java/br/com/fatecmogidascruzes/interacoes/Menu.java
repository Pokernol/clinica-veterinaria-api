package br.com.fatecmogidascruzes.interacoes;

import br.com.fatecmogidascruzes.entities.*;
import br.com.fatecmogidascruzes.singleton.SingletonDados;

import java.util.Scanner;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private SingletonDados dados = SingletonDados.getInstancia();

    public void iniciar() {
        int opcao;
        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            switch (opcao) {
                case 1:
                    cadastrarPet();
                    break;
                case 2:
                    agendarConsulta();
                    break;
                case 3:
                    registrarDiagnostico();
                    break;
                case 4:
                    cadastrarPrescricao();
                    break;
                case 5:
                    listarPets();
                    break;
                case 6:
                    listarConsultas();
                    break;
                case 7:
                    listarDiagnosticos();
                    break;
                case 8:
                    listarPrescricoes();
                    break;
                case 9:
                    listarTodosOsDados();
                    break;
                case 10:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        } while (opcao != 10);

        scanner.close(); // Fecha o Scanner para evitar o warning
    }

    private void exibirMenu() {
        System.out.println("\nEscolha uma opção:");
        System.out.println("1 - Cadastrar Pet");
        System.out.println("2 - Agendar Consulta");
        System.out.println("3 - Registrar Diagnóstico");
        System.out.println("4 - Cadastrar Prescrição Médica");
        System.out.println("5 - Listar Pets");
        System.out.println("6 - Listar Consultas");
        System.out.println("7 - Listar Diagnósticos");
        System.out.println("8 - Listar Prescrições");
        System.out.println("9 - Listar Todos os Dados");
        System.out.println("10 - Sair");
    }

    private void cadastrarPet() {
        Pet novoPet = CadastroPet.cadastrarPet();
        dados.adicionarPet(novoPet);
    }

    private void agendarConsulta() {
        Consulta consulta = CadastroConsulta.cadastrarConsulta(dados.getPets());
        if (consulta != null) {
            dados.adicionarConsulta(consulta);
        }
    }

    private void registrarDiagnostico() {
        Diagnostico diagnostico = CadastroDiagnostico.cadastrarDiagnostico(dados.getPets());
        if (diagnostico != null) {
            dados.adicionarDiagnostico(diagnostico);
        }
    }

    private void cadastrarPrescricao() {
        Prescricao prescricao = CadastroPrescricao.cadastrarPrescricao(dados.getPets());
        if (prescricao != null) {
            dados.adicionarPrescricao(prescricao);
        }
    }

    private void listarPets() {
        System.out.println("Lista de Pets:");
        for (Pet pet : dados.getPets()) {
            System.out.println(pet);
        }
    }

    private void listarConsultas() {
        System.out.println("Lista de Consultas:");
        for (Consulta consulta : dados.getConsultas()) {
            consulta.realizarServico();
        }
    }

    private void listarDiagnosticos() {
        System.out.println("Lista de Diagnósticos:");
        for (Diagnostico diagnostico : dados.getDiagnosticos()) {
            diagnostico.realizarServico();
        }
    }

    private void listarPrescricoes() {
        System.out.println("Lista de Prescrições:");
        for (Prescricao prescricao : dados.getPrescricoes()) {
            prescricao.realizarServico();
        }
    }

    private void listarTodosOsDados() {
        System.out.println("\nLista Completa de Dados:");
        listarPets();
        listarConsultas();
        listarDiagnosticos();
        listarPrescricoes();
    }
}