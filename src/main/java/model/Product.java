package model;

public class Product {

    private int id;
    private String name;
    private String description;
    private String type;
    private double price;
    private String country;
    private double TVA;
    private int quantity;
    private String supplierName;
    private static int count = 0;

    public Product() {
        this.id = count++;
    }

    public Product(String name, String description, String type, double price, String country, double TVA, int quantity, String supplierName) {
        this.id = count++;
        this.name = name;
        this.description = description;
        this.type = type;
        this.price = price;
        this.country = country;
        this.TVA = TVA;
        this.quantity = quantity;
        this.supplierName = supplierName;
    }

    @Override
    public String toString() {
        return  id +
                "," + name +
                "," + description +
                "," + type +
                "," + price +
                "," + country +
                "," + TVA +
                "," + quantity +
                "," + supplierName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getTVA() {
        return TVA;
    }

    public void setTVA(double TVA) {
        this.TVA = TVA;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
