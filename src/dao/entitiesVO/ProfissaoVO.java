package dao.entitiesVO;

import entities.Profissao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProfissaoVO extends BaseDAO {
    private long id;
    private String nome;
    private String descricao;

    public ProfissaoVO() {
    }

    public ProfissaoVO(long id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "Id = " + this.id + "\n" +
                "Nome = " + this.nome + "\n" +
                "Descrição = " + this.descricao;
    }

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

    public int alterarDB(long idProfissao, int opcaoAlterar, String alteracao) {
        // Testado
        connection = this.getConnection();

        int retorno;

        String nomeColuna;

        retorno = 2;

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

    public List<ProfissaoVO> obterProfissaoNome(String nomeProfissao) {
        // Testado
        connection = this.getConnection();

        List<ProfissaoVO> list = new ArrayList<>();

        ResultSet resultSet;

        String sql = "SELECT * FROM profissao WHERE nome LIKE ?";

        String nomePesquisa = "%" + nomeProfissao + "%";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nomePesquisa.toUpperCase());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ProfissaoVO profissaoVO = new ProfissaoVO();
                profissaoVO.setId(resultSet.getInt("id"));
                profissaoVO.setNome(resultSet.getString("nome"));
                profissaoVO.setDescricao(resultSet.getString("descricao"));
                list.add(profissaoVO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<ProfissaoVO> obterProfissaoId(long idProfissao) {
        // Testado
        connection = this.getConnection();

        List<ProfissaoVO> list = new ArrayList<>();

        ResultSet resultSet;

        ProfissaoVO profissaoVO = new ProfissaoVO();

        String sql = "SELECT * FROM profissao WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, idProfissao);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                profissaoVO.setId(resultSet.getInt("id"));
                profissaoVO.setNome(resultSet.getString("nome"));
                profissaoVO.setDescricao(resultSet.getString("descricao"));
                list.add(profissaoVO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<ProfissaoVO> listarDB() {
        // Testado
        connection = this.getConnection();

        List<ProfissaoVO> list;

        ResultSet resultSet;

        String sql = "SELECT * FROM profissao ORDER BY nome";

        list = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                ProfissaoVO profissaoDBTemporaria = new ProfissaoVO();
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
