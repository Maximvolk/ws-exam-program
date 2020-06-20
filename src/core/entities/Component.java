package core.entities;

public class Component {
    public int id;
    public String name;
    public long serialNumber;
    public String productName;

    public Component(int id, String name, long serialNumber, String productName) {
        this.id = id;
        this.name = name;
        this.serialNumber = serialNumber;
        this.productName = productName;
    }
}
