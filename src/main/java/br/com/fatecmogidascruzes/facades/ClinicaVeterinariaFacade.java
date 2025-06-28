package br.com.fatecmogidascruzes.facades;

import br.com.fatecmogidascruzes.adapters.DonoAdapter;
import br.com.fatecmogidascruzes.builders.*;
import br.com.fatecmogidascruzes.dao.*;
import br.com.fatecmogidascruzes.dao.impl.*;
import br.com.fatecmogidascruzes.dtos.DonoComDetalhes;
import br.com.fatecmogidascruzes.entities.*;
import br.com.fatecmogidascruzes.exceptions.DadoDuplicadoException;
import br.com.fatecmogidascruzes.factories.ConnectionFactorySingleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.sql.SQLException;

public class ClinicaVeterinariaFacade {

    private static final Logger logger = LoggerFactory.getLogger(ClinicaVeterinariaFacade.class);

    private final DonoDAO donoDAO;
    private final VeterinarioDAO veterinarioDAO;
    private final PetDAO petDAO;
    private final ConsultaDAO consultaDAO;
    private final DiagnosticoDAO diagnosticoDAO;
    private final PrescricaoDAO prescricaoDAO;
    private final DonoAdapter donoAdapter;

    public ClinicaVeterinariaFacade(ConnectionFactorySingleton connectionFactory) {
        this.donoDAO = new DonoDAOImpl(connectionFactory);
        this.veterinarioDAO = new VeterinarioDAOImpl(connectionFactory);
        this.petDAO = new PetDAOImpl(connectionFactory);
        this.consultaDAO = new ConsultaDAOImpl(connectionFactory);
        this.diagnosticoDAO = new DiagnosticoDAOImpl(connectionFactory);
        this.prescricaoDAO = new PrescricaoDAOImpl(connectionFactory);
        this.donoAdapter = new DonoAdapter(petDAO);
    }

    // --- Métodos de Cadastro ---

    public void registrarNovoDono(String nome, String telefone, String email) throws SQLException {
        Dono dono = new Dono(0, nome, telefone, email);
        try {
            donoDAO.add(dono);
        } catch (DadoDuplicadoException e) {
            logger.warn("Erro de negócio ao registrar novo dono: {}", e.getMessage());
            throw e;
        } catch (SQLException e) {
            logger.error("Erro de banco de dados ao registrar novo dono: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void registrarNovoVeterinario(String nome, String crmv, String especialidade) throws SQLException {
        Veterinario veterinario = new Veterinario(0, nome, crmv, especialidade);
        try {
            veterinarioDAO.add(veterinario);
        } catch (DadoDuplicadoException e) {
            logger.warn("Erro de negócio ao registrar novo veterinário: {}", e.getMessage());
            throw e;
        } catch (SQLException e) {
            logger.error("Erro de banco de dados ao registrar novo veterinário: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void registrarNovoPet(String nome, String especie, String raca, LocalDate dataNascimento, int donoId) throws SQLException {
        if (donoDAO.findById(donoId) == null) {
            throw new IllegalArgumentException("Dono com ID " + donoId + " não encontrado.");
        }

        Pet novoPet = new PetBuilder()
                .withNome(nome)
                .withEspecie(especie)
                .withRaca(raca)
                .withDataNascimento(dataNascimento)
                .withDonoId(donoId)
                .build();
        petDAO.add(novoPet);
    }

    public void agendarConsulta(int petId, int veterinarioId, LocalDateTime dataHora, String motivoConsulta) throws SQLException {
        if (petDAO.findById(petId) == null) {
            throw new IllegalArgumentException("Pet com ID " + petId + " não encontrado.");
        }
        if (veterinarioDAO.findById(veterinarioId) == null) {
            throw new IllegalArgumentException("Veterinário com ID " + veterinarioId + " não encontrado.");
        }

        Consulta novaConsulta = new ConsultaBuilder()
                .withPetId(petId)
                .withVeterinarioId(veterinarioId)
                .withDataHora(dataHora)
                .withMotivoConsulta(motivoConsulta)
                .build();
        consultaDAO.add(novaConsulta);
    }

    public int registrarDiagnostico(int petId, int consultaId, String descricao, String gravidade) throws SQLException {
        if (petDAO.findById(petId) == null) {
            throw new IllegalArgumentException("Pet com ID " + petId + " não encontrado.");
        }
        if (consultaId != 0 && consultaDAO.findById(consultaId) == null) {
            throw new IllegalArgumentException("Consulta com ID " + consultaId + " não encontrada para vincular o diagnóstico.");
        }

        Diagnostico novoDiagnostico = new DiagnosticoBuilder()
                .withPetId(petId)
                .withConsultaId(consultaId)
                .withDescricao(descricao)
                .withGravidade(gravidade)
                .build();
        diagnosticoDAO.add(novoDiagnostico);
        return novoDiagnostico.getId();
    }

    public int registrarPrescricao(int petId, int veterinarioId, int consultaId, String medicamento, String dosagem, String instrucoesUso) throws SQLException {
        if (petDAO.findById(petId) == null) {
            throw new IllegalArgumentException("Pet com ID " + petId + " não encontrado.");
        }
        if (veterinarioDAO.findById(veterinarioId) == null) {
            throw new IllegalArgumentException("Veterinário com ID " + veterinarioId + " não encontrado.");
        }
        if (consultaId != 0 && consultaDAO.findById(consultaId) == null) {
            System.out.println("Atenção: Consulta com ID " + consultaId + " não encontrada. Prescrição será registrada sem vincular a esta consulta.");
            consultaId = 0;
        }

        Prescricao novaPrescricao = new PrescricaoBuilder()
                .withPetId(petId)
                .withVeterinarioId(veterinarioId)
                .withConsultaId(consultaId)
                .withMedicamento(medicamento)
                .withDosagem(dosagem)
                .withInstrucoesUso(instrucoesUso)
                .build();
        prescricaoDAO.add(novaPrescricao);
        return novaPrescricao.getId();
    }

    // --- Métodos de Atualização ---

    public void atualizarConsulta(Consulta consulta) throws SQLException {
        try {
            consultaDAO.update(consulta);
        } catch (SQLException e) {
            logger.error("Erro de banco de dados ao atualizar consulta: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void atualizarPrescricao(Prescricao prescricao) throws SQLException {
        try {
            prescricaoDAO.update(prescricao);
        } catch (SQLException e) {
            logger.error("Erro de banco de dados ao atualizar prescricao: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void atualizarDiagnostico(Diagnostico diagnostico) throws SQLException {
        try {
            diagnosticoDAO.update(diagnostico);
        } catch (SQLException e) {
            logger.error("Erro de banco de dados ao atualizar diagnostico: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void atualizarDono(Dono dono) throws SQLException {
        try {
            donoDAO.update(dono);
        } catch (SQLException e) {
            logger.error("Erro de banco de dados ao atualizar dono: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void atualizarVeterinario(Veterinario veterinario) throws SQLException {
        try {
            veterinarioDAO.update(veterinario);
        } catch (SQLException e) {
            logger.error("Erro de banco de dados ao atualizar veterinario: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void atualizarPet(Pet pet) throws SQLException {
        try {
            petDAO.update(pet);
        } catch (SQLException e) {
            logger.error("Erro de banco de dados ao atualizar pet: {}", e.getMessage(), e);
            throw e;
        }
    }

    // --- Métodos de Exclusão ---

    public void excluirConsulta(int id) throws SQLException {
        try {
            consultaDAO.delete(id);
        } catch (SQLException e) {
            logger.error("Erro de banco de dados ao excluir consulta: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void excluirPrescricao(int id) throws SQLException {
        try {
            prescricaoDAO.delete(id);
        } catch (SQLException e) {
            logger.error("Erro de banco de dados ao excluir prescricao: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void excluirDiagnostico(int id) throws SQLException {
        try {
            diagnosticoDAO.delete(id);
        } catch (SQLException e) {
            logger.error("Erro de banco de dados ao excluir diagnostico: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void excluirDono(int id) throws SQLException {
        try {
            donoDAO.delete(id);
        } catch (SQLException e) {
            logger.error("Erro de banco de dados ao excluir dono: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void excluirVeterinario(int id) throws SQLException {
        try {
            veterinarioDAO.delete(id);
        } catch (SQLException e) {
            logger.error("Erro de banco de dados ao excluir veterinario: {}", e.getMessage(), e);
            throw e;
        }
    }

    public void excluirPet(int id) throws SQLException {
        try {
            petDAO.delete(id);
        } catch (SQLException e) {
            logger.error("Erro de banco de dados ao excluir pet: {}", e.getMessage(), e);
            throw e;
        }
    }

    // --- Métodos de Listagem ---

    public List<Dono> listarTodosDonos() throws SQLException {
        try {
            return donoDAO.findAll();
        } catch (SQLException e) {
            logger.error("Erro de banco de dados ao listar todos os donos: {}", e.getMessage(), e);
            throw e;
        }
    }

    public Dono buscarDonoPorId(int id) throws SQLException {
        try {
            return donoDAO.findById(id);
        } catch (SQLException e) {
            logger.error("Erro de banco de dados ao buscar dono por ID: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<Veterinario> listarTodosVeterinarios() throws SQLException {
        try {
            return veterinarioDAO.findAll();
        } catch (SQLException e) {
            logger.error("Erro de banco de dados ao listar todos os veterinários: {}", e.getMessage(), e);
            throw e;
        }
    }

    public Veterinario buscarVeterinarioPorId(int id) throws SQLException {
        try {
            return veterinarioDAO.findById(id);
        } catch (SQLException e) {
            logger.error("Erro de banco de dados ao buscar veterinario por ID: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<Pet> listarTodosPets() throws SQLException {
        try {
            return petDAO.findAll();
        } catch (SQLException e) {
            logger.error("Erro de banco de dados ao listar todos os pets: {}", e.getMessage(), e);
            throw e;
        }
    }

    public Pet buscarPetPorId(int id) throws SQLException {
        try {
            return petDAO.findById(id);
        } catch (SQLException e) {
            logger.error("Erro de banco de dados ao buscar pet por ID: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<Consulta> listarTodasConsultas() throws SQLException {
        try {
            return consultaDAO.findAll();
        } catch (SQLException e) {
            logger.error("Erro de banco de dados ao listar todas as consultas: {}", e.getMessage(), e);
            throw e;
        }
    }

    public Consulta buscarConsultaPorId(int id) throws SQLException {
        try {
            return consultaDAO.findById(id);
        } catch (SQLException e) {
            logger.error("Erro de banco de dados ao buscar consulta por ID: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<Consulta> buscarConsultasPorPetId(int petId) throws SQLException {
        try {
            return consultaDAO.findByPetId(petId);
        } catch (SQLException e) {
            logger.error("Erro de banco de dados ao buscar consultas por pet ID: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<Consulta> buscarConsultasPorVeterinarioId(int veterinarioId) throws SQLException {
        try {
            return consultaDAO.findByVeterinarioId(veterinarioId);
        } catch (SQLException e) {
            logger.error("Erro de banco de dados ao buscar consultas por veterinario ID: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<Consulta> buscarConsultasPorDataHora(LocalDateTime dataHora) throws SQLException {
        try {
            return consultaDAO.findByDataHora(dataHora);
        } catch (SQLException e) {
            logger.error("Erro de banco de dados ao buscar consultas por data/hora: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<Consulta> buscarConsultasPorPrescricaoId(int prescricaoId) throws SQLException {
        try {
            return consultaDAO.findByPrescricaoId(prescricaoId);
        } catch (SQLException e) {
            logger.error("Erro de banco de dados ao buscar consultas por prescricao ID: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<Diagnostico> listarTodosDiagnosticos() throws SQLException {
        try {
            return diagnosticoDAO.findAll();
        } catch (SQLException e) {
            logger.error("Erro de banco de dados ao listar todos os diagnósticos: {}", e.getMessage(), e);
            throw e;
        }
    }

    public Diagnostico buscarDiagnosticoPorId(int id) throws SQLException {
        try {
            return diagnosticoDAO.findById(id);
        } catch (SQLException e) {
            logger.error("Erro de banco de dados ao buscar diagnostico por ID: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<Prescricao> listarTodasPrescricoes() throws SQLException {
        try {
            return prescricaoDAO.findAll();
        } catch (SQLException e) {
            logger.error("Erro de banco de dados ao listar todas as prescrições: {}", e.getMessage(), e);
            throw e;
        }
    }

    public Prescricao buscarPrescricaoPorId(int id) throws SQLException {
        try {
            return prescricaoDAO.findById(id);
        } catch (SQLException e) {
            logger.error("Erro de banco de dados ao buscar prescricao por ID: {}", e.getMessage(), e);
            throw e;
        }
    }

    public List<DonoComDetalhes> listarDonosComPets() throws SQLException {
        try {
            List<Dono> donos = donoDAO.findAll();
            return donoAdapter.adapt(donos);
        } catch (SQLException e) {
            logger.error("Erro de banco de dados ao listar donos com pets: {}", e.getMessage(), e);
            throw e;
        }
    }

    public DonoComDetalhes buscarDonoComPetsPorPetId(int petId) throws SQLException {
        try {
            Dono dono = donoDAO.findByPetId(petId);
            return donoAdapter.adapt(dono);
        } catch (SQLException e) {
            logger.error("Erro de banco de dados ao obter dono com pets por ID {}: {}", petId, e.getMessage(), e);
            throw e;
        }
    }

}