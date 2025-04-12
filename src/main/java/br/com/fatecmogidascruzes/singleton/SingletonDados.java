package br.com.fatecmogidascruzes.singleton;

import br.com.fatecmogidascruzes.entities.*;

import java.util.ArrayList;
import java.util.List;

public class SingletonDados {
    private static SingletonDados instancia;

    private List<Pet> pets = new ArrayList<>();
    private List<Consulta> consultas = new ArrayList<>();
    private List<Diagnostico> diagnosticos = new ArrayList<>();
    private List<Prescricao> prescricoes = new ArrayList<>();

    private SingletonDados() {}

    public static SingletonDados getInstancia() {
        if (instancia == null) {
            instancia = new SingletonDados();
        }
        return instancia;
    }

    public void adicionarPet(Pet pet) {
        pets.add(pet);
    }

    public void adicionarConsulta(Consulta consulta) {
        consultas.add(consulta);
    }

    public void adicionarDiagnostico(Diagnostico diagnostico) {
        diagnosticos.add(diagnostico);
    }

    public void adicionarPrescricao(Prescricao prescricao) {
        prescricoes.add(prescricao);
    }

    public List<Pet> getPets() {
        return pets;
    }

    public List<Consulta> getConsultas() {
        return consultas;
    }

    public List<Diagnostico> getDiagnosticos() {
        return diagnosticos;
    }

    public List<Prescricao> getPrescricoes() {
        return prescricoes;
    }
}