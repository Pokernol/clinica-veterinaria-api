package br.com.fatecmogidascruzes.entities;

import java.time.LocalDate; // Importar java.time

public class Pet {
    private int id;
    private String nome;
    private String especie;
    private String raca;
    private LocalDate dataNascimento;
    private int donoId;

    public Pet(String nome, String especie, int donoId) {
        this.nome = nome;
        this.especie = especie;
        this.donoId = donoId;
        this.raca = null;
        this.dataNascimento = null;
    }

    public Pet(String nome, String especie, String raca, LocalDate dataNascimento, int donoId) {
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.dataNascimento = dataNascimento;
        this.donoId = donoId;
    }

    public Pet(int id, String nome, String especie, String raca, LocalDate dataNascimento, int donoId) {
        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.dataNascimento = dataNascimento;
        this.donoId = donoId;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEspecie() {
        return especie;
    }

    public String getRaca() {
        return raca;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public int getDonoId() {
        return donoId;
    }

    public void setId(int id) {
        if (this.id == 0) {
            this.id = id;
        }
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public void setDonoId(int donoId) {
        this.donoId = donoId;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "\n   id=" + id +
                ",\n    nome='" + nome + '\'' +
                ",\n    especie='" + especie + '\'' +
                ",\n    raca='" + raca + '\'' +
                ",\n    dataNascimento=" + dataNascimento +
                ",\n    donoId=" + donoId +
                "\n}";
    }
}