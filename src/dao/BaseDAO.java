package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class BaseDAO {
    protected Connection connection = null;

    public Connection getConnection() {
        String url = "jdbc:postgresql://localhost:5432/trabalho4";
        String user = "gabri";
        String password = "456";

        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return connection;
        }

        return connection;
    }
}
