package data;

import core.entities.*;
import core.IProductRepository;
import data.database.IDbConnector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ProductRepository implements IProductRepository {
    private IDbConnector dbConnector;

    public ProductRepository(IDbConnector dbConnector) {
        this.dbConnector = dbConnector;
    }

    public boolean doesProductExist(String name) throws SQLException {
        ResultSet result = dbConnector.selectQuery(
                String.format("select * from product where name = '%s'", name));

        return result.next();
    }

    public void addProduct(String name, String label) throws SQLException {
        dbConnector.executeQuery(String.format("insert into product values (null, '%s', '%s')", name, label));
    }

    public List<Product> getProducts() throws SQLException {
        ResultSet result = dbConnector.selectQuery("select * from product");

        List<Product> productsList = new ArrayList<>();
        while (result.next()) {
            Product product = new Product(result.getInt("id"),
                    result.getString("name"), result.getString("label"));
            productsList.add(product);
        }

        return productsList;
    }

    public Product getProductByName(String name) throws SQLException {
        ResultSet result = dbConnector.selectQuery(
                String.format("select * from product where name = '%s'", name));

        // Assert element retrieval to indicate internal error (invalid name passed)
        boolean hasNext = result.next();
        assert hasNext;

        return new Product(result.getInt("id"), result.getString("name"), result.getString("label"));
    }
}
