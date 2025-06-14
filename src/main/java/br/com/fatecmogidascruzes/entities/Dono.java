package br.com.fatecmogidascruzes.entities;

public class Dono {
    private int id;
    private String nome;
    private String telefone;
    private String email;

    public Dono(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public Dono(int id, String nome, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        if (this.id == 0) {
            this.id = id;
        }
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Dono{" +
                "\n   id=" + id +
                ",\n    nome='" + nome + '\'' +
                ",\n    telefone='" + telefone + '\'' +
                ",\n    email='" + email + '\'' +
                "\n}";
    }
}