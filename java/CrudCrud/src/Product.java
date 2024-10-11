public class Product {
    private String id = "";
    private String name = "";
    private double price = 0F;
    private int nIds = 0;


    public Product() {}

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
        this.id = generateID();
    }


    private String generateID() {
        return Integer.toString(nIds++);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "Product {" +
                " id = '" + getId() + "'" +
                ", name = '" + getName() + "'" +
                ", price = '" + getPrice() + "'" +
                "}";
    }
}
