package modelo;

import java.util.Arrays;

public class Pessoa {
    private String nome;
    private Profissao profissao;
    private String[] telefone;

    public Pessoa() {
    }

    public Pessoa(String nome, Profissao profissao, String[] telefone) {
        this.nome = nome;
        this.profissao = profissao;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Profissao getProfissao() {
        return profissao;
    }

    public void setProfissao(Profissao profissao) {
        this.profissao = profissao;
    }

    public String[] getTelefone() {
        return telefone;
    }

    public void setTelefone(String[] telefone) {
        this.telefone = telefone;
    }

    @Override
    public String toString() {
        return "Nome = " + this.nome + ", " +
                this.profissao + ", " +
                "Telefone(s) = " + Arrays.toString(telefone);
    }
}
