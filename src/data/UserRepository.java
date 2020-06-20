package data;

import core.IUserRepository;
import core.entities.User;
import data.database.IDbConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserRepository implements IUserRepository {
    private IDbConnector dbConnector;

    public UserRepository(IDbConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    public Optional<User> findUserByCredentials(String login, String password) throws SQLException {
        ResultSet user = dbConnector.selectQuery(
                String.format("select * from users where login = '%s' and password = '%s'", login, password));

        if (!user.next())
            return Optional.empty();

        return Optional.of(new User(user.getInt("id"), user.getString("login"), user.getString("password")));
    }
}
