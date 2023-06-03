package entities;

public class Pessoa {
    private Integer id;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
