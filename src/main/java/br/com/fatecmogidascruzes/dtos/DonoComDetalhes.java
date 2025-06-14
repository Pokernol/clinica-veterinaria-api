package br.com.fatecmogidascruzes.dtos;

import br.com.fatecmogidascruzes.entities.Pet;

import java.util.List;

public class DonoComDetalhes {
    private int id;
    private String nome;
    private String telefone;
    private String email;
    private List<Pet> pets;

    public DonoComDetalhes(int id, String nome, String telefone, String email, List<Pet> pets) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.pets = pets;
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

    public List<Pet> getPets() {
        return pets;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id)
                .append(", Nome: ").append(nome)
                .append(", Telefone: ").append(telefone)
                .append(", Email: ").append(email);
        if (pets != null && !pets.isEmpty()) {
            sb.append("\n  Pets:");
            for (Pet pet : pets) {
                sb.append("\n    - ").append(pet.getNome()).append(" (Espécie: ").append(pet.getEspecie()).append(")");
            }
        } else {
            sb.append("\n  Nenhum pet cadastrado.");
        }
        return sb.toString();
    }
}