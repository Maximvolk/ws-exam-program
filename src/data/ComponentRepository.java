package data;

import core.IComponentRepository;
import core.entities.Component;
import data.database.IDbConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ComponentRepository implements IComponentRepository {
    private IDbConnector dbConnector;

    public ComponentRepository(IDbConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    public List<Component> getComponents() throws SQLException {
        ResultSet result = dbConnector.selectQuery(
                "select component.id as id, component.name as name, serialNumber, product.name as productName " +
                        "from component inner join product on component.productId = product.id");

        List<Component> componentsList = new ArrayList<>();
        while (result.next()) {
            Component component = new Component(result.getInt("id"), result.getString("name"),
                    result.getLong("serialNumber"), result.getString("productName"));
            componentsList.add(component);
        }

        return componentsList;
    }

    public boolean doesComponentExist(String name) throws SQLException {
        ResultSet result = dbConnector.selectQuery(String.format("select * from component where name = '%s'", name));
        return result.next();
    }

    public void addComponent(String name, long serialNumber, int productId) throws SQLException {
        dbConnector.executeQuery(String.format(
                "insert into component values (null, '%s', %d, %d)", name, serialNumber, productId));
    }

    public void deleteComponent(String name) throws SQLException {
        dbConnector.executeQuery(String.format("delete from component where name = '%s'", name));
    }

    public void updateComponent(String oldName, String newName,
                                long newSerialNumber, int newProductId) throws SQLException {
        dbConnector.executeQuery(String.format(
                "update component set name = '%s', serialNumber = %d, productId = %d where name = '%s'",
                newName, newSerialNumber, newProductId, oldName
        ));
    }
}
