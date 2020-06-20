package presenter;

import javax.swing.*;

public class LoginView extends JFrame {
    private JTextField loginField;
    private JTextField passwordField;
    private JButton logInButton;
    private JPanel mainPanel;

    public LoginView() {
        setContentPane(mainPanel);
        setTitle("Login");
        pack();
    }

    public JButton getLogInButton() {
        return logInButton;
    }

    public String getLogin() {
        return loginField.getText();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public void clearInputs() {
        loginField.setText(null);
        passwordField.setText(null);
    }
}
