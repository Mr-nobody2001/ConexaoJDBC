package entities.dao.impl;

import db.BaseDAO;
import entities.dao.TelefoneDAO;
import entities.Telefone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TelefoneDaoJDBC extends BaseDAO implements TelefoneDAO {
    private final Connection CONNECTION;
    public TelefoneDaoJDBC() {
        this.CONNECTION = getConnection();
    }

    public void inserir(List<Telefone> numeros, long idPessoa) {
        // Testada
        String sql = "INSERT INTO telefone (numero, id_pessoa) VALUES (?, ?)";

        for (Telefone numero : numeros) {
            try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
                preparedStatement.setString(1, numero.getNumero().trim());
                preparedStatement.setLong(2, idPessoa);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public int alterar(Telefone telefone) {
        // Testado
        int retorno = -1;

        String sql = "UPDATE telefone SET numero = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            preparedStatement.setString(1, telefone.getNumero().trim());
            preparedStatement.setLong(2, telefone.getId());
            retorno = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return retorno;
    }

    public void remover(long idPessoa) {
        // Testada
        String sql = "DELETE FROM telefone WHERE id_pessoa = ?";

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            preparedStatement.setLong(1, idPessoa);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int removerTelefoneEspeficico(long idPessoa, long idTelefone) {
        // Testada
        int retorno = -1;

        String sql = "DELETE FROM telefone WHERE id_pessoa = ? AND id = ?";

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            preparedStatement.setLong(1, idPessoa);
            preparedStatement.setLong(2, idTelefone);
            retorno = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return retorno;
    }

    public Telefone procurarTelefoneId(long idPessoa, long idTelefone) {
        // Testado
        Telefone telefone = null;

        ResultSet resultSet = null;

        String sql = "SELECT * FROM telefone WHERE id_pessoa = ? AND id = ?";

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            preparedStatement.setLong(1, idPessoa);
            preparedStatement.setLong(2, idTelefone);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                telefone = new Telefone();
                telefone.setId(resultSet.getLong("id"));
                telefone.setNumero(resultSet.getString("numero"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            fecharResultSet(resultSet);
        }

        return telefone;
    }

    public List<Telefone> listarTelefone(long idPessoa) {
        // Testado
        List<Telefone> list = new ArrayList<>();

        ResultSet resultSet = null;

        String sql = "SELECT * FROM telefone WHERE id_pessoa = ?";

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
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
        return this.listarTelefone(idPessoa).size() == 0;
    }
}
