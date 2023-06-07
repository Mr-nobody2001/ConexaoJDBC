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
        connection = getConnection();

        String sql = "INSERT INTO telefone (numero, id_pessoa) VALUES (?, ?)";

        for (Telefone numero : numeros) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, numero.getNumero());
                preparedStatement.setLong(2, idPessoa);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int alterarDB(long idTelefone, String alteracao) {
        // Testado
        connection = getConnection();

        int retorno = -1;

        String sql = "UPDATE telefone SET numero = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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
        connection = getConnection();

        String sql = "DELETE FROM telefone WHERE id_pessoa = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, idPessoa);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int removerDbEspeficico(long idPessoa, long idTelefone) {
        // Testada
        connection = getConnection();

        int retorno = -1;

        String sql = "DELETE FROM telefone WHERE id_pessoa = ? AND id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
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
        connection = getConnection();

        List<Telefone> list = new ArrayList<>();

        ResultSet resultSet = null;

        String sql = "SELECT * FROM telefone WHERE id_pessoa = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, idPessoa);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Telefone telefone = new Telefone();
                telefone.setId(resultSet.getLong("id"));
                telefone.setNumero(resultSet.getString("numero"));
                list.add(telefone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            fecharResultSet(resultSet);
        }

        return list;
    }

    public boolean bancoVazio(long idPessoa) {
        connection = getConnection();

        ResultSet resultSet = null;

        String sql = "SELECT * FROM telefone WHERE id_pessoa = ?";

        boolean bancoVazio = true;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, idPessoa);
            resultSet = preparedStatement.executeQuery();
            bancoVazio = !resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            fecharResultSet(resultSet);
        }

        return bancoVazio;
    }
}
