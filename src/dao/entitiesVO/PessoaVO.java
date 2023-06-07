package dao.entitiesVO;

import entities.Pessoa;
import entities.exceptions.SqlInsertException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PessoaVO extends BaseDAO {
    public long inserirBD(Pessoa pessoa) {
        // Testada
        connection = getConnection();

        ResultSet resultSet = null;

        String sql = "INSERT INTO pessoa (nome, id_profissao) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, pessoa.getNome().toUpperCase());
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

    public int alterarDB(long idPessoa, int opcaoAlterar, String alteracao) {
        // Testado
        connection = getConnection();

        int retorno = -1;
        String nomeColuna = null;

        switch (opcaoAlterar) {
            case 1 -> nomeColuna = "nome";
            case 2 -> nomeColuna = "id_profissao";
        }

        String sql = "UPDATE pessoa SET " + nomeColuna + " = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            if (opcaoAlterar == 1) {
                preparedStatement.setString(1, alteracao.toUpperCase());
            } else {
                preparedStatement.setInt(1, Integer.parseInt(alteracao));
            }

            preparedStatement.setLong(2, idPessoa);
            retorno = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return retorno;
    }

    public int removerDB(long idPessoa) {
        // Testada
        connection = getConnection();

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

    public List<Pessoa> obterPessoa(String nomePessoa) {
        // Testado
        connection = getConnection();

        List<Pessoa> list = new ArrayList<>();

        ResultSet resultSet = null;

        ProfissaoVO profissaoVO;

        String sql = "SELECT * FROM pessoa WHERE nome LIKE ? ORDER BY id";

        String nomePesquisa = "%" + nomePessoa + "%";

        profissaoVO = new ProfissaoVO();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, nomePesquisa.toUpperCase());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setId(resultSet.getLong("id"));
                pessoa.setNome(resultSet.getString("nome"));
                pessoa.setProfissao(profissaoVO.obterProfissaoId(resultSet.getInt("id_profissao")).get(0));
                list.add(pessoa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            fecharResultSet(resultSet);
        }

        return list;
    }

    public List<Pessoa> listarDB() {
        // Testado
        connection = getConnection();

        List<Pessoa> list;

        ResultSet resultSet = null;

        ProfissaoVO profissaoVO;

        String sql = "SELECT * FROM pessoa ORDER BY nome";

        list = new ArrayList<>();

        profissaoVO = new ProfissaoVO();

        try (Statement statement = connection.createStatement()) {
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setId(resultSet.getLong("id"));
                pessoa.setNome(resultSet.getString("nome"));
                pessoa.setProfissao(profissaoVO.obterProfissaoId(resultSet.getInt("id_profissao")).get(0));
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
        connection = getConnection();

        ResultSet resultSet = null;

        String sql = "SELECT * FROM pessoa";

        boolean bancoVazio;

        bancoVazio = true;

        try (Statement statement = connection.createStatement();) {
            resultSet = statement.executeQuery(sql);
            bancoVazio = !resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            fecharResultSet(resultSet);
        }

        return bancoVazio;
    }
}
