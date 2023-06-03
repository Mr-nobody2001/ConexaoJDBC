package entities;

public class Telefone {
    private Integer id;
    private String numero;

    public Telefone() {
    }

    public Telefone(String numero) {
        this.numero = numero;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
