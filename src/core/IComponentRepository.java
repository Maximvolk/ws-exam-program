package core;

import core.entities.Component;

import java.sql.SQLException;
import java.util.List;

public interface IComponentRepository {
    List<Component> getComponents() throws SQLException;
    boolean doesComponentExist(String name) throws SQLException;
    void addComponent(String name, long serialNumber, int productId) throws SQLException;
    void deleteComponent(String name) throws SQLException;
    void updateComponent(String oldName, String newName,
                         long newSerialNumber, int newProductId) throws SQLException;
}
