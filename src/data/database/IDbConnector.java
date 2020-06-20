package data.database;

import java.sql.*;

public interface IDbConnector {
    ResultSet selectQuery(String query) throws SQLException;
    void executeQuery(String query) throws SQLException;
}
