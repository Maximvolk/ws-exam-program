package core;

import core.entities.*;
import java.sql.SQLException;
import java.util.List;

public interface IProductRepository {
    boolean doesProductExist(String name) throws SQLException;
    void addProduct(String name, String label) throws SQLException;
    List<Product> getProducts() throws SQLException;
    Product getProductByName(String name) throws SQLException;
}
