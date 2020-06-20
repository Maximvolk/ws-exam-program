package core.usecases;

import core.IUserRepository;
import core.entities.User;

import java.sql.SQLException;
import java.util.Optional;

public class Account {
    private IUserRepository repository;

    public Account(IUserRepository repository) {
        this.repository = repository;
    }

    public boolean logIn(String login, String password) throws SQLException {
        Optional<User> user = repository.findUserByCredentials(login, password);
        return user.isPresent();
    }
}
