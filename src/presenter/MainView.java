package presenter;

import core.entities.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class MainView extends JFrame {
    private JPanel mainPanel;
    private JTabbedPane mainWindowTabsPanel;
    private JList<String> productsList;
    private JTextField productName;
    private JTextField productLabel;
    private JButton addProductButton;
    private JPanel productsPanel;
    private JPanel componentsPanel;
    private JTable componentsTable;
    private JTextField componentName;
    private JTextField componentSerialNumber;
    private JButton addComponentButton;
    private JButton logoutButton;
    private JButton editComponentButton;
    private JComboBox<Product> productNameComboBox;
    private JButton deleteComponentButton;

    private DefaultListModel<String> productsListModel = new DefaultListModel<>();
    private DefaultTableModel componentsTableModel = new DefaultTableModel();

    public MainView() {
        setContentPane(mainPanel);
        productsList.setModel(productsListModel);

        componentsTableModel.addColumn("Name");
        componentsTableModel.addColumn("Serial number");
        componentsTableModel.addColumn("Product name");
        componentsTable.setModel(componentsTableModel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Main window!");
        pack();
    }

    JButton getLogoutButton() {
        return logoutButton;
    }

    JButton getAddProductButton() {
        return addProductButton;
    }

    String getProductName() {
        return productName.getText();
    }

    String getProductLabel() {
        return productLabel.getText();
    }

    String getComponentName() {
        return componentName.getText();
    }

    String getComponentSerialNumber() {
        return componentSerialNumber.getText();
    }

    JButton getAddComponentButton() {
        return addComponentButton;
    }

    Product getProductNameComboBoxValue() {
        return (Product)productNameComboBox.getSelectedItem();
    }

    JButton getDeleteComponentButton() {
        return deleteComponentButton;
    }

    JButton getEditComponentButton() {
        return editComponentButton;
    }

    JTable getComponentsTable() {
        return componentsTable;
    }

    DefaultTableModel getComponentsTableModel() {
        return componentsTableModel;
    }

    void setComponentName(String name) {
        componentName.setText(name);
    }

    void setComponentSerialNumber(long serialNumber) {
        componentSerialNumber.setText(Long.toString(serialNumber));
    }

    void setProductNameComboBoxValue(Product product) {
        productNameComboBox.setSelectedItem(product);
    }

    void clearProductInputs() {
        productName.setText(null);
        productLabel.setText(null);
    }

    void clearComponentInputs() {
        componentName.setText(null);
        componentSerialNumber.setText(null);
    }

    void fillProductsList(List<Product> products) {
        productsListModel.clear();

        for (Product product : products)
            productsListModel.addElement(product.name);
    }

    void fillComponentsTable(List<Component> components) {
        for (Component component : components)
            componentsTableModel.addRow(new Object[] {component.name, component.serialNumber, component.productName});
    }

    void fillProductNameComboBoxOptions(List<Product> products) {
        productNameComboBox.removeAllItems();

        for (Product product : products)
            productNameComboBox.addItem(product);
    }
}
