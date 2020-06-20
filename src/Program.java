import core.usecases.*;
import data.*;
import data.database.*;
import presenter.LoginController;
import presenter.LoginView;
import presenter.MainController;
import presenter.MainView;

public class Program {
    public static void main(String[] args) {
        IDbConnector dbConnector = new DbConnector();

        Account accountUseCase = new Account(new UserRepository(dbConnector));
        ProductUseCase productUseCase = new ProductUseCase(new ProductRepository(dbConnector));
        ComponentUseCase componentUseCase = new ComponentUseCase(new ComponentRepository(dbConnector));

        LoginView loginView = new LoginView();
        MainView mainView = new MainView();

        LoginController loginController = new LoginController(loginView, mainView, accountUseCase);
        MainController mainController = new MainController(loginView, mainView, productUseCase, componentUseCase);

        loginController.setLoginViewVisible(true);
    }
}
