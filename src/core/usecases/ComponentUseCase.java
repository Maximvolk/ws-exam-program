package core.usecases;

import core.IComponentRepository;
import core.entities.Component;

import java.sql.SQLException;
import java.util.List;

public class ComponentUseCase {
    private IComponentRepository componentRepository;

    public ComponentUseCase(IComponentRepository componentRepository) {
        this.componentRepository = componentRepository;
    }

    public List<Component> getComponents() throws SQLException {
        return componentRepository.getComponents();
    }

    public int addComponent(String name, long serialNumber, int productId) throws SQLException {
        // Do not check productId on existence because its options are loaded from db
        if (componentRepository.doesComponentExist(name))
            return 1;

        componentRepository.addComponent(name, serialNumber, productId);
        return 0;
    }

    public void deleteComponent(String name) throws SQLException {
        componentRepository.deleteComponent(name);
    }

    public int editComponent(String oldName, String newName,
                             long newSerialNumber, int newProductId) throws SQLException {
        if (!oldName.equals(newName)) {
            if (componentRepository.doesComponentExist(newName))
                return 1;
        }

        componentRepository.updateComponent(oldName, newName, newSerialNumber, newProductId);
        return 0;
    }
}
