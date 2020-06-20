package presenter;

import core.usecases.Account;

import javax.swing.*;
import java.sql.SQLException;

public class LoginController {
    private LoginView loginView;
    private MainView mainView;
    private Account accountUseCase;

    public LoginController(LoginView loginView, MainView mainView, Account account) {
        this.loginView = loginView;
        this.mainView = mainView;
        this.accountUseCase = account;

        setUpViewHandlers();
    }

    private void setUpViewHandlers() {
        loginView.getLogInButton().addActionListener(actionEvent -> handleLogInButton());
    }

    private void handleLogInButton() {
        boolean loggedIn;

        try {
            loggedIn = accountUseCase.logIn(loginView.getLogin(), loginView.getPassword());
        } catch (SQLException ex) {
            ExceptionHandlingUtil.handleUnexpectedError(ex, loginView);
            return;
        }

        if (loggedIn) {
            mainView.setVisible(true);
            loginView.setVisible(false);
        } else
            JOptionPane.showMessageDialog(
                    loginView, "Invalid credentials", "Error", JOptionPane.ERROR_MESSAGE);

    }

    public void setLoginViewVisible(boolean visible) {
        loginView.setVisible(visible);
    }
}
