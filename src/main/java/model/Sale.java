package model;

import controller.ProductController;

import java.util.Map;

public class Sale {

    private int id;
    private String storeName;
    private int staffId;
    private int clientId;
    private Map<Integer,Integer> products; // key: products id, value: quantity
    private double totalPrice;
    private static int count = 0;
    public Sale(String storeName, int staff, int client, Map<Integer, Integer> product) {
        this.id = count++;
        this.storeName = storeName;
        this.staffId = staff;
        this.clientId = client;
        this.products = product;
        this.totalPrice = calculateTotalPrice();
    }

    private double calculateTotalPrice() {
        ProductController productController = new ProductController();
        double totalPrice = 0.0;
        if(products == null || products.isEmpty())
            return totalPrice;
        for (Map.Entry<Integer, Integer> entry : products.entrySet()) {
            Integer productId = entry.getKey();
            Integer quantity = entry.getValue();
            Product p = productController.getProductById(productId);
            totalPrice+=quantity*p.getPrice();
        }
        return totalPrice;
    }

    public Sale() {
    }

    @Override
    public String toString() {
        return "" + id +
                "," + storeName +
                "," + staffId +
                "," + clientId +
                "," + products +
                "," + totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public Map<Integer, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Integer, Integer> products) {
        this.products = products;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
