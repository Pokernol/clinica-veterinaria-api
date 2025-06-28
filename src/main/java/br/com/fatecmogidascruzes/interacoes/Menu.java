package br.com.fatecmogidascruzes.interacoes;

import br.com.fatecmogidascruzes.entities.*;
import br.com.fatecmogidascruzes.facades.ClinicaVeterinariaFacade;
import br.com.fatecmogidascruzes.exceptions.DadoDuplicadoException;
import br.com.fatecmogidascruzes.factories.ConnectionFactorySingleton;
import br.com.fatecmogidascruzes.dtos.DonoComDetalhes;

import java.sql.SQLException;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Menu {
    private final Scanner scanner;
    private final ClinicaVeterinariaFacade facade;
    private static final Logger logger = LoggerFactory.getLogger(Menu.class);

    public Menu() {
        this.scanner = new Scanner(System.in);
        ConnectionFactorySingleton connectionFactory = ConnectionFactorySingleton.getInstancia();
        this.facade = new ClinicaVeterinariaFacade(connectionFactory);
    }

    public void exibirMenu() {
        int opcao;
        do {
            System.out.println("\n--- Menu Principal da Clínica Veterinária ---");
            System.out.println("1. Cadastrar Dono");
            System.out.println("2. Cadastrar Veterinário");
            System.out.println("3. Cadastrar Pet");
            System.out.println("4. Agendar Consulta");
            System.out.println("5. Registrar Diagnóstico");
            System.out.println("6. Registrar Prescrição");
            System.out.println("7. Listar Donos");
            System.out.println("8. Listar Veterinários");
            System.out.println("9. Listar Pets");
            System.out.println("10. Listar Consultas");
            System.out.println("11. Listar Diagnósticos");
            System.out.println("12. Listar Prescrições");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1: cadastrarDono(); break;
                    case 2: cadastrarVeterinario(); break;
                    case 3: cadastrarPet(); break;
                    case 4: agendarConsulta(); break;
                    case 5: registrarDiagnostico(); break;
                    case 6: registrarPrescricao(); break;
                    case 7: listarDonos(); break;
                    case 8: listarVeterinarios(); break;
                    case 9: listarPets(); break;
                    case 10: listarConsultas(); break;
                    case 11: listarDiagnosticos(); break;
                    case 12: listarPrescricoes(); break;
                    case 0: System.out.println("Saindo..."); break;
                    default: System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                logger.warn("Entrada do usuário inválida (não numérica).", e);
                scanner.nextLine();
                opcao = -1;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } while (opcao != 0);
        scanner.close();
    }

    // --- Métodos de Cadastro ---

    private void cadastrarDono() {
        System.out.println("\n--- Cadastrar Novo Dono ---");
        System.out.print("Nome do Dono: ");
        String nome = scanner.nextLine();
        System.out.print("Telefone do Dono: ");
        String telefone = scanner.nextLine();
        System.out.print("Email do Dono: ");
        String email = scanner.nextLine();

        try {
            facade.registrarNovoDono(nome, telefone, email);
            System.out.println("Dono cadastrado com sucesso!");
        } catch (DadoDuplicadoException e) {
            System.out.println("Erro ao cadastrar dono: " + e.getMessage());
            logger.warn("Tentativa de cadastro de dono duplicado: {}", e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro no banco de dados ao cadastrar dono: " + e.getMessage());
            logger.error("Erro de DB ao cadastrar dono: SQLState: {} - Mensagem: {}", e.getSQLState(), e.getMessage(), e);
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado ao cadastrar o dono. Por favor, contate o suporte.");
            logger.error("Erro inesperado ao cadastrar dono", e);
        }
    }

    private void cadastrarVeterinario() {
        System.out.println("\n--- Cadastrar Novo Veterinário ---");
        System.out.print("Nome do Veterinário: ");
        String nome = scanner.nextLine();
        System.out.print("CRMV: ");
        String crmv = scanner.nextLine();
        System.out.print("Especialidade: ");
        String especialidade = scanner.nextLine();

        try {
            facade.registrarNovoVeterinario(nome, crmv, especialidade);
            System.out.println("Veterinário cadastrado com sucesso!");
        } catch (DadoDuplicadoException e) {
            System.out.println("Erro ao cadastrar veterinário: " + e.getMessage());
            logger.warn("Tentativa de cadastro de veterinário duplicado: {}", e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro no banco de dados ao cadastrar veterinário: " + e.getMessage());
            logger.error("Erro de DB ao cadastrar veterinário: SQLState: {} - Mensagem: {}", e.getSQLState(), e.getMessage(), e);
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado ao cadastrar o veterinário. Por favor, contate o suporte.");
            logger.error("Erro inesperado ao cadastrar veterinário", e);
        }
    }

    private void cadastrarPet() throws SQLException {
        List<Dono> donos = facade.listarTodosDonos();
        if (donos.isEmpty()) {
            System.out.println("Nenhum dono cadastrado para cadastrar pet.");
            return;
        }

        System.out.println("\n--- Cadastrar Novo Pet ---");
        System.out.print("Nome do Pet: ");
        String nome = scanner.nextLine();
        System.out.print("Espécie: ");
        String especie = scanner.nextLine();
        System.out.print("Raça: ");
        String raca = scanner.nextLine();
        System.out.print("Data de Nascimento (AAAA-MM-DD): ");
        String dataNascimentoStr = scanner.nextLine();
        System.out.println("Escolha o dono para o pet:");
        Dono donoEscolhido = EscolherEntidade.escolher(scanner, donos);
        if (donoEscolhido == null) return;

        try {
            LocalDate dataNascimento = LocalDate.parse(dataNascimentoStr);
            facade.registrarNovoPet(nome, especie, raca, dataNascimento, donoEscolhido.getId());
            System.out.println("Pet cadastrado com sucesso!");
        } catch (DateTimeParseException e) {
            System.out.println("Formato de data inválido. Use AAAA-MM-DD.");
            logger.warn("Formato de data inválido ao cadastrar pet: {}", dataNascimentoStr);
        } catch (SQLException e) {
            System.out.println("Erro no banco de dados ao cadastrar pet: " + e.getMessage());
            logger.error("Erro de DB ao cadastrar pet: SQLState: {} - Mensagem: {}", e.getSQLState(), e.getMessage(), e);
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado ao cadastrar o pet. Por favor, contate o suporte.");
            logger.error("Erro inesperado ao cadastrar pet", e);
        }
    }

    private void agendarConsulta() throws SQLException {
        System.out.println("\n--- Agendar Nova Consulta ---");
        List<Pet> pets = facade.listarTodosPets();
        if (pets.isEmpty()) {
            System.out.println("Nenhum pet cadastrado para agendar consulta.");
            return;
        }
        System.out.println("Escolha o pet para agendar a consulta:");
        Pet petEscolhido = EscolherEntidade.escolher(scanner, pets);
        if (petEscolhido == null) return;

        List<Veterinario> veterinarios = facade.listarTodosVeterinarios();
        if (veterinarios.isEmpty()) {
            System.out.println("Nenhum veterinário cadastrado para agendar consulta.");
            return;
        }
        System.out.println("Escolha o veterinário para a consulta:");
        Veterinario veterinarioEscolhido = EscolherEntidade.escolher(scanner, veterinarios);
        if (veterinarioEscolhido == null) return;

        System.out.print("Data e Hora da Consulta (AAAA-MM-DDTHH:MM): ");
        String dataHoraStr = scanner.nextLine();

        System.out.print("Motivo da consulta: ");
        String motivo = scanner.nextLine();

        try {
            LocalDateTime dataHora = LocalDateTime.parse(dataHoraStr);
            facade.agendarConsulta(petEscolhido.getId(), veterinarioEscolhido.getId(), dataHora, motivo);
            System.out.println("Consulta agendada com sucesso!");
        } catch (DateTimeParseException e) {
            System.out.println("Formato de data/hora inválido. Use AAAA-MM-DDTHH:MM (ex: 2023-10-26T14:30).");
            logger.warn("Formato de data/hora inválido ao agendar consulta: {}", dataHoraStr);
        } catch (DadoDuplicadoException e) {
            System.out.println("Erro ao agendar consulta: " + e.getMessage());
            logger.warn("Tentativa de agendamento de consulta duplicado: {}", e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro no banco de dados ao agendar consulta: " + e.getMessage());
            logger.error("Erro de DB ao agendar consulta: SQLState: {} - Mensagem: {}", e.getSQLState(), e.getMessage(), e);
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado ao agendar a consulta. Por favor, contate o suporte.");
            logger.error("Erro inesperado ao agendar consulta", e);
        }
    }

    private void registrarDiagnostico() throws SQLException {
        System.out.println("\n--- Registrar Diagnóstico ---");
        List<Pet> pets = facade.listarTodosPets();
        if (pets.isEmpty()) {
            System.out.println("Nenhum pet cadastrado para registrar diagnóstico.");
            return;
        }
        System.out.println("Escolha o pet para registrar o diagnóstico:");
        Pet petEscolhido = EscolherEntidade.escolher(scanner, pets);
        if (petEscolhido == null) return;

        List<Consulta> consultasDoPet = facade.buscarConsultasPorPetId(petEscolhido.getId());
        if (consultasDoPet.isEmpty()) {
            System.out.println("Nenhuma consulta encontrada para este pet.");
            return;
        }
        System.out.println("Escolha a consulta para registrar o diagnóstico:");
        Consulta consultaEscolhida = EscolherEntidade.escolher(scanner, consultasDoPet);
        if (consultaEscolhida == null) return;

        System.out.print("Descrição do diagnóstico: ");
        String descricao = scanner.nextLine();
        System.out.print("Gravidade do diagnóstico (Leve, Moderada, Grave): ");
        String gravidade = scanner.nextLine();

        try {
            int diagnosticoId = facade.registrarDiagnostico(petEscolhido.getId(), consultaEscolhida.getId(), descricao, gravidade);
            consultaEscolhida.setDiagnosticoId(diagnosticoId);
            if (consultaEscolhida.getDiagnosticoId() == 0) {
                System.out.println("Erro ao registrar diagnóstico. Diagnóstico ID inválido.");
                return;
            }
            facade.atualizarConsulta(consultaEscolhida);

            System.out.println("Diagnóstico registrado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro no banco de dados ao registrar diagnóstico: " + e.getMessage());
            logger.error("Erro de DB ao registrar diagnóstico: SQLState: {} - Mensagem: {}", e.getSQLState(), e.getMessage(), e);
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado ao registrar o diagnóstico. Por favor, contate o suporte.");
            logger.error("Erro inesperado ao registrar diagnóstico", e);
        }
    }

    private void registrarPrescricao() throws SQLException {
        System.out.println("\n--- Cadastrar Prescrição Médica ---");
        List<Pet> pets = facade.listarTodosPets();
        if (pets.isEmpty()) {
            System.out.println("Nenhum pet cadastrado para cadastrar prescrição.");
            return;
        }
        System.out.println("Escolha o pet para a prescrição:");
        Pet petEscolhido = EscolherEntidade.escolher(scanner, pets);
        if (petEscolhido == null) return;

        List<Veterinario> veterinarios = facade.listarTodosVeterinarios();
        if (veterinarios.isEmpty()) {
            System.out.println("Nenhum veterinário cadastrado para a prescrição.");
            return;
        }
        System.out.println("Escolha o veterinário que fará a prescrição:");
        Veterinario veterinarioEscolhido = EscolherEntidade.escolher(scanner, veterinarios);
        if (veterinarioEscolhido == null) return;

        List<Consulta> consultasDoPet = facade.buscarConsultasPorPetId(petEscolhido.getId());
        if (consultasDoPet.isEmpty()) {
            System.out.println("Nenhuma consulta encontrada para este pet para vincular a prescrição.");
            System.out.print("Deseja continuar sem vincular a uma consulta existente? (s/n): ");
            String resp = scanner.nextLine();
            if (!resp.equalsIgnoreCase("s")) {
                return;
            }
        }
        Consulta consultaEscolhida = null;
        int consultaId = 0;
        if (!consultasDoPet.isEmpty()) {
            System.out.println("Escolha a consulta para vincular a prescrição (opcional):");
            consultaEscolhida = EscolherEntidade.escolher(scanner, consultasDoPet);
            if (consultaEscolhida != null) {
                consultaId = consultaEscolhida.getId();
            }
        }

        System.out.print("Nome do medicamento: ");
        String medicamento = scanner.nextLine();
        System.out.print("Dosagem: ");
        String dosagem = scanner.nextLine();
        System.out.print("Instruções de Uso: ");
        String instrucoesUso = scanner.nextLine();

        try {
            int prescricaoId = facade.registrarPrescricao(petEscolhido.getId(), veterinarioEscolhido.getId(), consultaId, medicamento, dosagem, instrucoesUso);
            consultaEscolhida.setPrescricaoId(prescricaoId);
            
            if (consultaEscolhida.getPrescricaoId() == 0) {
                System.out.println("Erro ao registrar prescrição. Prescrição ID inválido.");
                return;
            }
            facade.atualizarConsulta(consultaEscolhida);

            System.out.println("Prescrição registrada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro no banco de dados ao registrar prescrição: " + e.getMessage());
            logger.error("Erro de DB ao registrar prescrição: SQLState: {} - Mensagem: {}", e.getSQLState(), e.getMessage(), e);
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado ao registrar a prescrição. Por favor, contate o suporte.");
            logger.error("Erro inesperado ao registrar prescrição", e);
        }
    }

    // --- Métodos de Listagem ---

    private void listarDonos() {
        System.out.println("\n--- Lista de Donos (com Pets) ---");
        try {
            List<DonoComDetalhes> donos = facade.listarDonosComPets();
            if (donos.isEmpty()) {
                System.out.println("Nenhum dono cadastrado.");
            } else {
                for (DonoComDetalhes dono : donos) {
                    System.out.println(dono);
                    System.out.println("--------------------");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro no banco de dados ao listar donos: " + e.getMessage());
            logger.error("Erro de DB ao listar donos: SQLState: {} - Mensagem: {}", e.getSQLState(), e.getMessage(), e);
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado ao listar os donos. Por favor, contate o suporte.");
            logger.error("Erro inesperado ao listar donos", e);
        }
    }

    private void listarVeterinarios() {
        System.out.println("\n--- Lista de Veterinários ---");
        try {
            List<Veterinario> veterinarios = facade.listarTodosVeterinarios();
            if (veterinarios.isEmpty()) {
                System.out.println("Nenhum veterinário cadastrado.");
            } else {
                for (Veterinario vet : veterinarios) {
                    System.out.println(vet);
                    System.out.println("--------------------");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro no banco de dados ao listar veterinários: " + e.getMessage());
            logger.error("Erro de DB ao listar veterinários: SQLState: {} - Mensagem: {}", e.getSQLState(), e.getMessage(), e);
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado ao listar os veterinários. Por favor, contate o suporte.");
            logger.error("Erro inesperado ao listar veterinários", e);
        }
    }

    private void listarPets() {
        System.out.println("\n--- Lista de Pets ---");
        try {
            List<Pet> pets = facade.listarTodosPets();
            if (pets.isEmpty()) {
                System.out.println("Nenhum pet cadastrado.");
            } else {
                for (Pet pet : pets) {
                    System.out.println(pet);
                    System.out.println("--------------------");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro no banco de dados ao listar pets: " + e.getMessage());
            logger.error("Erro de DB ao listar pets: SQLState: {} - Mensagem: {}", e.getSQLState(), e.getMessage(), e);
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado ao listar os pets. Por favor, contate o suporte.");
            logger.error("Erro inesperado ao listar pets", e);
        }
    }

    private void listarConsultas() {
        System.out.println("\n--- Lista de Consultas ---");
        try {
            List<Consulta> consultas = facade.listarTodasConsultas();
            if (consultas.isEmpty()) {
                System.out.println("Nenhuma consulta agendada.");
            } else {
                for (Consulta cons : consultas) {
                    System.out.println(cons);
                    System.out.println("--------------------");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro no banco de dados ao listar consultas: " + e.getMessage());
            logger.error("Erro de DB ao listar consultas: SQLState: {} - Mensagem: {}", e.getSQLState(), e.getMessage(), e);
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado ao listar as consultas. Por favor, contate o suporte.");
            logger.error("Erro inesperado ao listar consultas", e);
        }
    }

    private void listarDiagnosticos() {
        System.out.println("\n--- Lista de Diagnósticos ---");
        try {
            List<Diagnostico> diagnosticos = facade.listarTodosDiagnosticos();
            if (diagnosticos.isEmpty()) {
                System.out.println("Nenhum diagnóstico registrado.");
            } else {
                for (Diagnostico diag : diagnosticos) {
                    System.out.println(diag);
                    System.out.println("--------------------");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro no banco de dados ao listar diagnósticos: " + e.getMessage());
            logger.error("Erro de DB ao listar diagnósticos: SQLState: {} - Mensagem: {}", e.getSQLState(), e.getMessage(), e);
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado ao listar os diagnósticos. Por favor, contate o suporte.");
            logger.error("Erro inesperado ao listar diagnósticos", e);
        }
    }

    private void listarPrescricoes() {
        System.out.println("\n--- Lista de Prescrições ---");
        try {
            List<Prescricao> prescricoes = facade.listarTodasPrescricoes();
            if (prescricoes.isEmpty()) {
                System.out.println("Nenhuma prescrição registrada.");
            } else {
                for (Prescricao pres : prescricoes) {
                    System.out.println(pres);
                    System.out.println("--------------------");
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro no banco de dados ao listar prescrições: " + e.getMessage());
            logger.error("Erro de DB ao listar prescrições: SQLState: {} - Mensagem: {}", e.getSQLState(), e.getMessage(), e);
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado ao listar as prescrições. Por favor, contate o suporte.");
            logger.error("Erro inesperado ao listar prescrições", e);
        }
    }

}