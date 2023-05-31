package dao.vo;

import dao.BaseDAO;
import modelo.Pessoa;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PessoaVO extends BaseDAO {
    private long id;
    private String nome;
    private long idProfissao;

    public PessoaVO() {
    }

    public PessoaVO(long id, String nome, long idProfissao) {
        this.id = id;
        this.nome = nome;
        this.idProfissao = idProfissao;
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

    public long getIdProfissao() {
        return idProfissao;
    }

    public void setIdProfissao(long idProfissao) {
        this.idProfissao = idProfissao;
    }

    @Override
    public String toString() {
        return "Id = " + this.id + ", " +
                "Nome = " + this.nome + ", " +
                "IdProfissao = " + this.idProfissao;
    }

    public void inserirBD(Object[] input) {
        // Testada
        connection = this.getConnection();

        List<PessoaVO> list;

        Pessoa pessoa;

        long idPessoa;

        long idProfissao;

        String sql = "INSERT INTO pessoa (nome, id_profissao) VALUES (?, ?)";

        pessoa = (Pessoa) input[0];
        idProfissao = (long) input[1];
        idPessoa = -1;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, pessoa.getNome().toUpperCase());
            preparedStatement.setLong(2, idProfissao);
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

    public List<PessoaVO> obterPessoa(String nomePessoa) {
        // Testado
        connection = this.getConnection();

        List<PessoaVO> list = new ArrayList<>();

        ResultSet resultSet;

        String sql = "SELECT * FROM pessoa WHERE nome LIKE ?";

        String nomePesquisa = "%" + nomePessoa + "%";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nomePesquisa.toUpperCase());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PessoaVO pessoaVO = new PessoaVO();
                pessoaVO.setId(resultSet.getInt("id"));
                pessoaVO.setNome(resultSet.getString("nome"));
                pessoaVO.setIdProfissao(resultSet.getInt("id_profissao"));
                list.add(pessoaVO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<PessoaVO> listarDB() {
        // Testado
        connection = this.getConnection();

        List<PessoaVO> list;

        ResultSet resultSet;

        String sql = "SELECT * FROM pessoa ORDER BY nome";

        list = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                PessoaVO pessoaVO = new PessoaVO();
                pessoaVO.setId(resultSet.getInt("id"));
                pessoaVO.setNome(resultSet.getString("nome"));
                pessoaVO.setIdProfissao(resultSet.getInt("id_profissao"));
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
