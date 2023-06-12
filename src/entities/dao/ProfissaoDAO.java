package entities.dao;

import entities.Profissao;
import java.util.List;

public interface ProfissaoDAO {
    void inserir(Profissao profissao);
    Integer alterar(Profissao profissao);
    int remover(long idProfissao);
    List<Profissao> pesquisarProfissaoNome(String nomeProfissao);
    Profissao pesquisarProfissaoId(long idProfissao);
    List<Profissao> listarProfissao();
    boolean bancoVazio();
}
