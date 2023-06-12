package entities.dao.impl;

import db.BaseDAO;
import entities.dao.PessoaDAO;
import entities.Pessoa;
import entities.exceptions.SqlInsertException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAOJDBC extends BaseDAO implements PessoaDAO {
    private final Connection connection;

    public PessoaDAOJDBC() {
        this.connection = getConnection();
    }

    public long inserir(Pessoa pessoa) {
        // Testada
        ResultSet resultSet = null;

        String sql = "INSERT INTO pessoa (nome, id_profissao) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, pessoa.getNome().toUpperCase().trim());
            preparedStatement.setLong(2, pessoa.getProfissao().getId());
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (!resultSet.next()) {
                throw new SqlInsertException("não foi possível inserir a nova pessoa");
            } else {
                return resultSet.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            fecharResultSet(resultSet);
        }
    }

    public int alterar(Pessoa pessoa) {
        // Testado
        int retorno = -1;

        String sql = "UPDATE pessoa SET nome = ?, id_profissao = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, pessoa.getNome().toUpperCase().trim());
            preparedStatement.setLong(2, pessoa.getProfissao().getId());
            preparedStatement.setLong(3, pessoa.getId());
            retorno = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return retorno;
    }

    public int remover(long idPessoa) {
        // Testada
        int retorno;

        String sql = "DELETE FROM pessoa WHERE id = ?";

        retorno = 2;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, idPessoa);
            retorno = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return retorno;
    }

    public List<Pessoa> pesquisarPessoa(String nomePessoa) {
        // Testado
        List<Pessoa> list = new ArrayList<>();

        ResultSet resultSet = null;

        ProfissaoDAOJDBC profissaoDAOJDBC;

        String sql = "SELECT * FROM pessoa WHERE nome LIKE ? ORDER BY id";

        String nomePesquisa = nomePessoa + "%";

        profissaoDAOJDBC = new ProfissaoDAOJDBC();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, nomePesquisa.toUpperCase());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setId(resultSet.getLong("id"));
                pessoa.setNome(resultSet.getString("nome"));
                pessoa.setProfissao(profissaoDAOJDBC.pesquisarProfissaoId(resultSet.getInt("id_profissao")));
                list.add(pessoa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            fecharResultSet(resultSet);
        }

        return list;
    }

    public List<Pessoa> listarPessoa() {
        // Testado
        List<Pessoa> list;

        ResultSet resultSet = null;

        ProfissaoDAOJDBC profissaoDAOJDBC;

        String sql = "SELECT * FROM pessoa ORDER BY nome";

        list = new ArrayList<>();

        profissaoDAOJDBC = new ProfissaoDAOJDBC();

        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setId(resultSet.getLong("id"));
                pessoa.setNome(resultSet.getString("nome"));
                pessoa.setProfissao(profissaoDAOJDBC.pesquisarProfissaoId(resultSet.getInt("id_profissao")));
                list.add(pessoa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            fecharResultSet(resultSet);
        }

        return list;
    }

    public boolean bancoVazio() {
       return this.listarPessoa().size() == 0;
    }
}
