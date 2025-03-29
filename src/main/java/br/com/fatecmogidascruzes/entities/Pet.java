package br.com.fatecmogidascruzes.entities;

public class Pet {
    private String nome;
    private String especie;
    private String dono;

    public Pet(String nome, String especie, String dono) {
        this.nome = nome;
        this.especie = especie;
        this.dono = dono;
    }

    public String getNome() {
        return nome;
    }

    public String getEspecie() {
        return especie;
    }

    public String getDono() {
        return dono;
    }
}
