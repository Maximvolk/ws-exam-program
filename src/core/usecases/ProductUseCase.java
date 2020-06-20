package core.usecases;

import core.entities.*;
import core.IProductRepository;

import java.sql.SQLException;
import java.util.List;

public class ProductUseCase {
    private IProductRepository productRepository;

    public ProductUseCase(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public int addProduct(String name, String label) throws SQLException {
        if (productRepository.doesProductExist(name))
            return 1;

        productRepository.addProduct(name, label);
        return 0;
    }

    public List<Product> getProducts() throws SQLException {
        return productRepository.getProducts();
    }

    public Product getProductByName(String name) throws SQLException {
        // Product existence is not checked because method is called on data from table in the view
        return productRepository.getProductByName(name);
    }
}
