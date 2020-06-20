package presenter;

import core.entities.*;
import core.usecases.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    private LoginView loginView;
    private MainView mainView;
    private ProductUseCase productUseCase;
    private ComponentUseCase componentUseCase;

    public MainController(LoginView loginView, MainView mainView,
                          ProductUseCase productUseCase, ComponentUseCase componentUseCase) {
        this.loginView = loginView;
        this.mainView = mainView;
        this.productUseCase = productUseCase;
        this.componentUseCase = componentUseCase;

        fillProductsData();
        fillComponentsData();
        setUpHandlers();
    }

    private void fillProductsData() {
        List<Product> products = new ArrayList<>();

        try {
            products = productUseCase.getProducts();
        } catch (SQLException ex) {
            ExceptionHandlingUtil.handleUnexpectedError(ex, mainView);
        }

        mainView.fillProductsList(products);
        mainView.fillProductNameComboBoxOptions(products);
    }

    private void fillComponentsData() {
        List<Component> components = new ArrayList<>();

        try {
            components = componentUseCase.getComponents();
        } catch (SQLException ex) {
            ExceptionHandlingUtil.handleUnexpectedError(ex, mainView);
        }

        mainView.fillComponentsTable(components);
    }

    private void setUpHandlers() {
        mainView.getLogoutButton().addActionListener(actionEvent -> handleLogoutButton());
        mainView.getAddProductButton().addActionListener(actionEvent -> handleAddProductButton());

        mainView.getAddComponentButton().addActionListener(actionEvent -> handleAddComponentButton());
        mainView.getDeleteComponentButton().addActionListener(actionEvent -> handleDeleteComponentButton());
        mainView.getEditComponentButton().addActionListener(actionEvent -> handleEditComponentButton());
        mainView.getComponentsTable().getSelectionModel()
                .addListSelectionListener(actionEvent -> handleComponentSelected());
    }

    private void handleLogoutButton() {
        mainView.setVisible(false);
        loginView.clearInputs();
        loginView.setVisible(true);
    }

    private void handleAddProductButton() {
        String name = mainView.getProductName();
        String label = mainView.getProductLabel();

        if (name.length() == 0 || label.length() == 0) {
            JOptionPane.showMessageDialog(mainView, "Data must not be empty",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int status;

        try {
            status = productUseCase.addProduct(name, label);
        } catch (SQLException ex) {
            ExceptionHandlingUtil.handleUnexpectedError(ex, mainView);
            return;
        }

        if (status == 0) {
            fillProductsData();
            mainView.clearProductInputs();
        }
        else if (status == 1)
            JOptionPane.showMessageDialog(mainView, "Product exists", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void handleAddComponentButton() {
        String name = mainView.getComponentName();
        String serialNumber = mainView.getComponentSerialNumber();
        Product product = mainView.getProductNameComboBoxValue();

        if (!checkEnteredComponentData(name, serialNumber))
            return;

        int status;

        try {
            status = componentUseCase.addComponent(name, Long.parseLong(serialNumber), product.id);
        } catch (SQLException ex) {
            ExceptionHandlingUtil.handleUnexpectedError(ex, mainView);
            return;
        }

        if (status == 0) {
            mainView.getComponentsTableModel().addRow(new Object[] {name, serialNumber, product.name});
            mainView.clearComponentInputs();
        }
        else
            JOptionPane.showMessageDialog(mainView, "Component exists", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void handleDeleteComponentButton() {
        int selectedRow = mainView.getComponentsTable().getSelectedRow();

        if (!checkComponentSelected(selectedRow))
            return;

        try {
            componentUseCase.deleteComponent(
                    mainView.getComponentsTable().getValueAt(selectedRow, 0).toString());
            mainView.getComponentsTableModel().removeRow(selectedRow);
            mainView.clearComponentInputs();
        } catch (SQLException ex) {
            ExceptionHandlingUtil.handleUnexpectedError(ex, mainView);
        }
    }

    private void handleEditComponentButton() {
        int selectedRow = mainView.getComponentsTable().getSelectedRow();
        String name = mainView.getComponentName();
        String serialNumber = mainView.getComponentSerialNumber();
        Product componentProduct = mainView.getProductNameComboBoxValue();

        if (!checkComponentSelected(selectedRow) || !checkEnteredComponentData(name, serialNumber))
            return;

        int status;

        try {
            status = componentUseCase.editComponent(
                    mainView.getComponentsTable().getValueAt(selectedRow, 0).toString(),
                    name, Long.parseLong(serialNumber), componentProduct.id
            );
        } catch (SQLException ex) {
            ExceptionHandlingUtil.handleUnexpectedError(ex, mainView);
            return;
        }

        if (status == 0) {
            mainView.getComponentsTableModel().insertRow(
                    selectedRow, new Object[] {name, serialNumber, componentProduct.name});
            mainView.getComponentsTableModel().removeRow(selectedRow + 1);
            mainView.clearComponentInputs();
        } else
            JOptionPane.showMessageDialog(
                    mainView, "New component name equals name of already existing component",
                    "Error", JOptionPane.ERROR_MESSAGE
            );
    }

    private void handleComponentSelected() {
        JTable componentsTable = mainView.getComponentsTable();
        int selectedRow = componentsTable.getSelectedRow();

        // Avoid event handling after component deletion
        if (selectedRow == -1)
            return;

        mainView.setComponentName(
                componentsTable.getValueAt(selectedRow, 0).toString());
        mainView.setComponentSerialNumber(
                Long.parseLong(componentsTable.getValueAt(selectedRow, 1).toString()));

        // This db query (every time) will cause much less performance overhead
        // than storing all products objects in table (memory)
        try {
            Product product = productUseCase.getProductByName(
                    componentsTable.getValueAt(selectedRow, 2).toString());
            mainView.setProductNameComboBoxValue(product);
        } catch (SQLException ex) {
            ExceptionHandlingUtil.handleUnexpectedError(ex, mainView);
        }
    }

    private boolean checkComponentSelected(int selectedRow) {
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(mainView, "No component selected", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private boolean checkEnteredComponentData(String name, String serialNumber) {
        if (name.length() == 0 || serialNumber.length() == 0) {
            JOptionPane.showMessageDialog(mainView, "Data must not be empty",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!serialNumber.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(mainView, "Serial number must be a number",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }
}
