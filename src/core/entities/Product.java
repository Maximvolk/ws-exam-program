package core.entities;

public class Product {
    public int id;
    public String name;
    public String label;

    public Product(int id, String name, String label) {
        this.id = id;
        this.name = name;
        this.label = label;
    }

    public String toString() {
        return name;
    }
}
