package core;

import core.entities.User;

import java.sql.SQLException;
import java.util.Optional;

public interface IUserRepository {
    Optional<User> findUserByCredentials(String login, String password) throws SQLException;
}
