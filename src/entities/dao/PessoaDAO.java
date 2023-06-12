package entities.dao;

import entities.Pessoa;
import java.util.List;

public interface PessoaDAO {
    long inserir(Pessoa pessoa);
    int alterar(Pessoa pessoa);
    int remover(long idPessoa);
    List<Pessoa> pesquisarPessoa(String nomePessoa);
    List<Pessoa> listarPessoa();
    boolean bancoVazio();
}
