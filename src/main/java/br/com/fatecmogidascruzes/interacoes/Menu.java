package br.com.fatecmogidascruzes.interacoes;

import br.com.fatecmogidascruzes.entities.Pet;
import br.com.fatecmogidascruzes.entities.Prescricao;
import br.com.fatecmogidascruzes.entities.Consulta;
import br.com.fatecmogidascruzes.entities.Diagnostico;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private List<Pet> pets = new ArrayList<>();
    private List<Consulta> consultas = new ArrayList<>();
    private List<Diagnostico> diagnosticos = new ArrayList<>();
    private List<Prescricao> prescricoes = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);

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
        pets.add(novoPet);
    }

    private void agendarConsulta() {
        Consulta consulta = CadastroConsulta.cadastrarConsulta(pets);
        if (consulta != null) {
            consultas.add(consulta);
        }
    }

    private void registrarDiagnostico() {
        Diagnostico diagnostico = CadastroDiagnostico.cadastrarDiagnostico(pets);
        if (diagnostico != null) {
            diagnosticos.add(diagnostico);
        }
    }

    private void cadastrarPrescricao() {
        Prescricao prescricao = CadastroPrescricao.cadastrarPrescricao(pets);
        if (prescricao != null) {
            prescricoes.add(prescricao);
        }
    }

    private void listarPets() {
        System.out.println("Lista de Pets:");
        for (Pet pet : pets) {
            System.out.println(pet);
        }
    }

    private void listarConsultas() {
        System.out.println("Lista de Consultas:");
        for (Consulta consulta : consultas) {
            consulta.realizarServico();
        }
    }

    private void listarDiagnosticos() {
        System.out.println("Lista de Diagnósticos:");
        for (Diagnostico diagnostico : diagnosticos) {
            diagnostico.realizarServico();
        }
    }

    private void listarPrescricoes() {
        System.out.println("Lista de Prescrições:");
        for (Prescricao prescricao : prescricoes) {
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
