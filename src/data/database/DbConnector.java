package data.database;

import java.sql.*;

// Connection is created on every request to db.
// This may result in a little overhead but allows to reestablish connection if it is lost
// (instead of creating in once on start)
public class DbConnector implements IDbConnector {
    public ResultSet selectQuery(String query) throws SQLException {
        Connection connection = setUpConnection();
        return connection.createStatement().executeQuery(query);
    }

    public void executeQuery(String query) throws SQLException {
        Connection connection = setUpConnection();
        connection.prepareStatement(query).executeUpdate();
    }

    private Connection setUpConnection() throws SQLException {
        return DriverManager.getConnection(Config.dbConnectionString, Config.dbUser, Config.dbPassword);
    }
}
