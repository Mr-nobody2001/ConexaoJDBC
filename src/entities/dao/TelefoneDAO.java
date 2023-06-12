package entities.dao;

import entities.Telefone;

import java.util.List;

public interface TelefoneDAO {
    void inserir(List<Telefone> numeros, long idPessoa);
    int alterar(Telefone telefone);
    void remover(long idPessoa);
    int removerTelefoneEspeficico(long idPessoa, long idTelefone);
    Telefone procurarTelefoneId(long idPessoa, long idTelefone);
    List<Telefone> listarTelefone(long idPessoa);
    boolean bancoVazio(long idPessoa);
}
