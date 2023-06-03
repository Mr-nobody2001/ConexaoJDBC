package dao.entitiesVO;

import entities.Profissao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProfissaoVO extends BaseDAO {
    public void inserirBD(Profissao profissao) {
        // Testada
        connection = this.getConnection();

        String sql = "INSERT INTO profissao (nome, descricao) VALUES (?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, profissao.getNome().toUpperCase());
            preparedStatement.setString(2, profissao.getDescricao().toUpperCase());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer alterarDB(long idProfissao, int opcaoAlterar, String alteracao) {
        // Testado
        connection = this.getConnection();

        int retorno = -1;

        String nomeColuna;

        switch (opcaoAlterar) {
            case 1 -> nomeColuna = "nome";
            case 2 -> nomeColuna = "descricao";
            default -> nomeColuna = null;
        }

        String sql = "UPDATE profissao SET " + nomeColuna + " = ? WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, alteracao.toUpperCase());
            preparedStatement.setLong(2, idProfissao);
            retorno = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return retorno;
    }

    public int removerDB(long idProfissao) {
        // Testada
        connection = this.getConnection();

        int retorno;

        retorno = 2;

        String sql = "DELETE FROM profissao WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, idProfissao);
            retorno = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return retorno;
    }

    public List<Profissao> obterProfissaoNome(String nomeProfissao) {
        // Testado
        connection = this.getConnection();

        List<Profissao> list = new ArrayList<>();

        ResultSet resultSet;

        String sql = "SELECT * FROM profissao WHERE nome LIKE ?";

        String nomePesquisa = "%" + nomeProfissao + "%";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nomePesquisa.toUpperCase());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Profissao profissao = new Profissao();
                profissao.setId(resultSet.getInt("id"));
                profissao.setNome(resultSet.getString("nome"));
                profissao.setDescricao(resultSet.getString("descricao"));
                list.add(profissao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Profissao> obterProfissaoId(long idProfissao) {
        // Testado
        connection = this.getConnection();

        List<Profissao> list = new ArrayList<>();

        ResultSet resultSet;

        Profissao profissao = new Profissao();

        String sql = "SELECT * FROM profissao WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, idProfissao);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                profissao.setId(resultSet.getInt("id"));
                profissao.setNome(resultSet.getString("nome"));
                profissao.setDescricao(resultSet.getString("descricao"));
                list.add(profissao);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Profissao> listarDB() {
        // Testado
        connection = this.getConnection();

        List<Profissao> list;

        ResultSet resultSet;

        String sql = "SELECT * FROM profissao ORDER BY nome";

        list = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Profissao profissaoDBTemporaria = new Profissao();
                profissaoDBTemporaria.setId(resultSet.getInt("id"));
                profissaoDBTemporaria.setNome(resultSet.getString("nome"));
                profissaoDBTemporaria.setDescricao(resultSet.getString("descricao"));
                list.add(profissaoDBTemporaria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean bancoVazio() {
        // Testado
        connection = this.getConnection();

        ResultSet resultSet;

        String sql = "SELECT * FROM profissao";

        boolean bancoVazio = true;

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
