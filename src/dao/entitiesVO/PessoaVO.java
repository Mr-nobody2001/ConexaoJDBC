package dao.entitiesVO;

import dao.entitiesVO.BaseDAO;
import entities.Pessoa;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PessoaVO extends BaseDAO {
    public void inserirBD(Pessoa pessoa) {
        // Testada
        connection = this.getConnection();

        List<Pessoa> list;

        long idPessoa;

        String sql = "INSERT INTO pessoa (nome, id_profissao) VALUES (?, ?)";

        idPessoa = -1;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, pessoa.getNome().toUpperCase());
            preparedStatement.setLong(2, pessoa.getProfissao().getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        list = this.obterPessoa(pessoa.getNome());

        if (list.size() > 0) {
            idPessoa = list.get(0).getId();
        }

        TelefoneVO telefoneVO = new TelefoneVO();

        if (idPessoa != -1 && pessoa.getTelefone() != null) {
            telefoneVO.inserirBD(new Object[]{pessoa.getTelefone(), idPessoa});
        }
    }

    public void alterarDB(long idPessoa, int opcaoAlterar, String alteracao) {
        // Testado
        connection = this.getConnection();

        String nomeColuna;

        switch (opcaoAlterar) {
            case 1 -> nomeColuna = "nome";
            case 2 -> nomeColuna = "id_profissao";
            default -> nomeColuna = null;
        }

        String sql = "UPDATE pessoa SET " + nomeColuna + " = ? WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            if (opcaoAlterar == 1) {
                preparedStatement.setString(1, alteracao.toUpperCase());
            } else {
                preparedStatement.setInt(1, Integer.parseInt(alteracao));
            }

            preparedStatement.setLong(2, idPessoa);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int removerDB(long idPessoa) {
        // Testada
        TelefoneVO telefoneVO;

        telefoneVO = new TelefoneVO();

        telefoneVO.removerDBExclusaoPessoa(idPessoa);

        connection = this.getConnection();

        int retorno;

        String sql = "DELETE FROM pessoa WHERE id = ?";

        retorno = 2;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, idPessoa);
            retorno = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return retorno;
    }

    public List<Pessoa> obterPessoa(String nomePessoa) {
        // Testado
        connection = this.getConnection();

        List<Pessoa> list = new ArrayList<>();

        ResultSet resultSet;

        ProfissaoVO profissaoVO;

        String sql = "SELECT * FROM pessoa WHERE nome LIKE ?";

        String nomePesquisa = "%" + nomePessoa + "%";

        profissaoVO = new ProfissaoVO();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nomePesquisa.toUpperCase());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setId(resultSet.getInt("id"));
                pessoa.setNome(resultSet.getString("nome"));
                pessoa.setProfissao(profissaoVO.obterProfissaoId(resultSet.getInt("id_profissao")).get(0));
                list.add(pessoa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Pessoa> listarDB() {
        // Testado
        connection = this.getConnection();

        List<Pessoa> list;

        ResultSet resultSet;

        ProfissaoVO profissaoVO;

        String sql = "SELECT * FROM pessoa ORDER BY nome";

        list = new ArrayList<>();

        profissaoVO = new ProfissaoVO();

        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Pessoa pessoaVO = new Pessoa();
                pessoaVO.setId(resultSet.getInt("id"));
                pessoaVO.setNome(resultSet.getString("nome"));
                pessoaVO.setProfissao(profissaoVO.obterProfissaoId(resultSet.getInt("id_profissao")).get(0));
                list.add(pessoaVO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean bancoVazio() {
        connection = this.getConnection();

        ResultSet resultSet;

        String sql = "SELECT * FROM pessoa";

        boolean bancoVazio;

        bancoVazio = true;

        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            bancoVazio = !resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bancoVazio;
    }
}
