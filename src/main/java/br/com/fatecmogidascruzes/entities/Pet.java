package br.com.fatecmogidascruzes.entities;

public class Pet {
    private String nome;
    private String especie;
    private String dono;
    private int idade;

    public Pet(String nome, String especie, String dono, int idade) {
        this.nome = nome;
        this.especie = especie;
        this.dono = dono;
        this.idade = idade;
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

    public int getIdade() {
        return idade;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public void setDono(String dono) {
        this.dono = dono;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "nome='" + nome + '\'' +
                ", especie='" + especie + '\'' +
                ", dono='" + dono + '\'' +
                ", idade=" + idade +
                '}';
    }
}
