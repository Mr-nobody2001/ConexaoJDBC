package dao.entitiesVO;

import entities.Telefone;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TelefoneVO extends BaseDAO {
    public void inserirBD(List<Telefone> numeros, long idPessoa) {
        // Testada
        connection = this.getConnection();

        String sql = "INSERT INTO telefone (numero, id_pessoa) VALUES (?, ?)";

        for (int i = 0; i < numeros.size(); i++) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, numeros.get(i).getNumero());
                preparedStatement.setLong(2, idPessoa);
                preparedStatement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int alterarDB(long idTelefone, String alteracao) {
        // Testado
        connection = this.getConnection();

        int retorno = -1;

        String sql = "UPDATE telefone SET numero = ? WHERE id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, alteracao);
            preparedStatement.setLong(2, idTelefone);
            retorno = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return retorno;
    }

    public void removerDbTotal(long idPessoa) {
        // Testada
        connection = this.getConnection();

        String sql = "DELETE FROM telefone WHERE id_pessoa = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, idPessoa);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int removerDbEspeficico(long idPessoa, long idTelefone) {
        // Testada
        connection = this.getConnection();

        int retorno = -1;

        String sql = "DELETE FROM telefone WHERE id_pessoa = ? AND id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, idPessoa);
            preparedStatement.setLong(2, idTelefone);
            retorno = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return retorno;
    }

    public List<Telefone> obterTelefone(long idPessoa) {
        // Testado
        connection = this.getConnection();

        List<Telefone> list = new ArrayList<>();

        ResultSet resultSet;

        String sql = "SELECT * FROM telefone WHERE id_pessoa = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, idPessoa);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Telefone telefone = new Telefone();
                telefone.setId(resultSet.getInt("id"));
                telefone.setNumero(resultSet.getString("numero"));
                list.add(telefone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean bancoVazio(long idPessoa) {
        connection = this.getConnection();

        ResultSet resultSet;

        String sql = "SELECT * FROM telefone WHERE id_pessoa = ?";

        boolean bancoVazio = true;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, idPessoa);
            resultSet = preparedStatement.executeQuery();
            bancoVazio = !resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bancoVazio;
    }
}
