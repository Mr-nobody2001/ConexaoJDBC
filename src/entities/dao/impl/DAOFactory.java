package entities.dao.impl;

import entities.dao.PessoaDAO;
import entities.dao.ProfissaoDAO;
import entities.dao.TelefoneDAO;

public abstract class DAOFactory {
    public static ProfissaoDAO criarProfissaoDao() {
        return new ProfissaoDAOJDBC();
    }

    public static PessoaDAO criarPessoaDao() {
        return new PessoaDAOJDBC();
    }

    public static TelefoneDAO criarTelefoneDao() {
        return new TelefoneDaoJDBC();
    }
}
