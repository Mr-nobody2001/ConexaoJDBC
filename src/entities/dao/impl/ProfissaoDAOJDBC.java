package entities.dao.impl;

import db.BaseDAO;
import entities.dao.ProfissaoDAO;
import entities.Profissao;
import entities.exceptions.IntegrityConstraintException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProfissaoDAOJDBC extends BaseDAO implements ProfissaoDAO {
    private final Connection connection;

    public ProfissaoDAOJDBC() {
        this.connection = getConnection();
    }

    public void inserir(Profissao profissao) {
        // Testada
        String sql = "INSERT INTO profissao (nome, descricao) VALUES (?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, profissao.getNome().toUpperCase().trim());
            preparedStatement.setString(2, profissao.getDescricao().toUpperCase().trim());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer alterar(Profissao profissao) {
        // Testado
        int retorno = -1;

        String sql = "UPDATE profissao SET nome = ?, descricao = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, profissao.getNome().toUpperCase().trim());
            preparedStatement.setString(2, profissao.getDescricao().toUpperCase().trim());
            preparedStatement.setLong(3, profissao.getId());
            retorno = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return retorno;
    }

    public int remover(long idProfissao) {
        // Testada
        int retorno;

        String sql = "DELETE FROM profissao WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setLong(1, idProfissao);
            retorno = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IntegrityConstraintException("a profissão não pode ser excluída pois isso fere a restrição " +
                    "de integridade referencial");
        }

        return retorno;
    }

    public List<Profissao> pesquisarProfissaoNome(String nomeProfissao) {
        // Testado
        List<Profissao> list = new ArrayList<>();

        ResultSet resultSet = null;

        String sql = "SELECT * FROM profissao WHERE nome LIKE ?";

        String nomePesquisa = nomeProfissao + "%";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, nomePesquisa.toUpperCase());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Profissao profissao = new Profissao();
                profissao.setId(resultSet.getLong("id"));
                profissao.setNome(resultSet.getString("nome"));
                profissao.setDescricao(resultSet.getString("descricao"));
                list.add(profissao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            fecharResultSet(resultSet);
        }

        return list;
    }

    public Profissao pesquisarProfissaoId(long idProfissao) {
        // Testado
        ResultSet resultSet = null;

        Profissao profissao = null;

        String sql = "SELECT * FROM profissao WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setLong(1, idProfissao);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                profissao = new Profissao();
                profissao.setId(resultSet.getLong("id"));
                profissao.setNome(resultSet.getString("nome"));
                profissao.setDescricao(resultSet.getString("descricao"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            fecharResultSet(resultSet);
        }

        return profissao;
    }

    public List<Profissao> listarProfissao() {
        // Testado
        List<Profissao> list;

        ResultSet resultSet = null;

        String sql = "SELECT * FROM profissao ORDER BY nome";

        list = new ArrayList<>();

        try (Statement statement = connection.createStatement();) {
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Profissao profissaoDBTemporaria = new Profissao();
                profissaoDBTemporaria.setId(resultSet.getLong("id"));
                profissaoDBTemporaria.setNome(resultSet.getString("nome"));
                profissaoDBTemporaria.setDescricao(resultSet.getString("descricao"));
                list.add(profissaoDBTemporaria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            fecharResultSet(resultSet);
        }

        return list;
    }

    public boolean bancoVazio() {
        return this.listarProfissao().size() == 0;
    }
}
