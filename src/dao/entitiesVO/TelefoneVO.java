package dao.entitiesVO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TelefoneVO extends BaseDAO {
    private long id;
    private String numero;
    private long idPessoa;

    public TelefoneVO() {
    }

    public TelefoneVO(long id, String numero, long idPessoa) {
        this.id = id;
        this.numero = numero;
        this.idPessoa = idPessoa;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public long getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(long idPessoa) {
        this.idPessoa = idPessoa;
    }

    @Override
    public String toString() {
        return "TelefoneVO{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", idPessoa=" + idPessoa +
                '}';
    }

    public void inserirBD(Object[] input) {
        // Testada
        connection = this.getConnection();

        long idPessoa;

        String[] numerosTelefone;

        String sql = "INSERT INTO telefone (numero, id_pessoa) VALUES (?, ?)";

        numerosTelefone = (String[]) input[0];
        idPessoa = (long) input[1];

        for (int i = 0; i < numerosTelefone.length; i++) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, numerosTelefone[i]);
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

    public void removerDBExclusaoPessoa(long idPessoa) {
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

    public int removerDBExclusaoTelefone(long idPessoa, long idTelefone) {
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

    public List<TelefoneVO> obterTelefone(long idPessoa) {
        // Testado
        connection = this.getConnection();

        List<TelefoneVO> list = new ArrayList<>();

        ResultSet resultSet;

        String sql = "SELECT * FROM telefone WHERE id_pessoa = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, idPessoa);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TelefoneVO telefoneDBTemporario = new TelefoneVO();
                telefoneDBTemporario.setId(resultSet.getInt("id"));
                telefoneDBTemporario.setNumero(resultSet.getString("numero"));
                telefoneDBTemporario.setIdPessoa(resultSet.getInt("id_pessoa"));
                list.add(telefoneDBTemporario);
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
