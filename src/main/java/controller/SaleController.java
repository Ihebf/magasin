package controller;

import model.Client;
import model.Product;
import model.Sale;
import model.Staff;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SaleController {
    private static final Path saleDB;
    private static final ProductController productController = new ProductController();
    private static final StaffController staffController = new StaffController();
    private static final ClientController clientController = new ClientController();

    static {
        saleDB = Paths.get(System.getProperty("user.dir") + "/src/main/resources/","saleDB.txt");
        if(!Files.exists(saleDB)) {
            try {
                Files.createFile(saleDB);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    // Test sale controller
    public static void main(String[] args) {
        SaleController saleController = new SaleController();
        Sale sale = new Sale();

        Client c = new Client("client1","client1@gmail.com","5100");
        clientController.addClient(c);

        Staff s = new Staff("firstName","lastName","personal address","job address","role",0);
        staffController.addStaff(s);

        Product p1 = new Product("product1","description1","AUTRE",100.0,"country1",10,50,"supplier1");
        Product p2 = new Product("product2","description2","AUTRE",200.0,"country2",5,100,"supplier2");
        productController.addProduct(p1);
        productController.addProduct(p2);
        Map<Integer,Integer> products = new HashMap<>();
        products.put(p1.getId(),20);
        products.put(p2.getId(),30);


        sale.setStoreName("store1");
        sale.setStaffId(s.getId());
        sale.setClientId(c.getId());
        sale.setProducts(products);

        saleController.addSale(sale);

        System.out.println("get all sales: "+saleController.getAllSales());

    }

    private double calculateTotalPrice(Map<Integer, Integer> products) {
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

    public boolean addSale(Sale sale) {
        try {
            Map<Integer, Integer> products = sale.getProducts();
            for (Map.Entry<Integer, Integer> entry : products.entrySet()) {
                Integer productId = entry.getKey();
                Integer quantity = entry.getValue();
                Product p = productController.getProductById(productId);
                if(quantity > p.getQuantity()){
                    return false;
                }
                p.setQuantity(p.getQuantity()-quantity);
                productController.editProduct(p);
            }
            sale.setTotalPrice(calculateTotalPrice(products));
            Files.writeString(saleDB, sale.toString()+"\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteSale(int id) {
        try {
            List<String> data = Files.readAllLines(saleDB)
                    .parallelStream()
                    .filter(l -> !(Integer.parseInt(l.split(",")[0]) == id))
                    .collect(Collectors.toList());
            Files.write(saleDB,data);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void editSale(Sale sale) {
        try {
            List<String> data = Files.readAllLines(saleDB);
            List<String> newData = new ArrayList<>();
            data.parallelStream()
                    .forEach(d -> {
                        if(Integer.parseInt(d.split(",")[0]) == sale.getId()){
                            Sale oldSale = convertLineToSale(d);
                            oldSale.setStoreName(sale.getStoreName());
                            oldSale.setStaffId(sale.getStaffId());
                            oldSale.setClientId(sale.getClientId());
                            oldSale.setProducts(sale.getProducts());
                            oldSale.setTotalPrice(sale.getTotalPrice());
                            newData.add(oldSale.toString());
                        } else {
                            newData.add(d);
                        }
                    });
            Files.write(saleDB, newData, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Sale getSaleById(int id) {
        try {
            return Files.readAllLines(saleDB)
                    .parallelStream()
                    .filter(l -> Integer.parseInt(l.split(",")[0]) == id)
                    .findFirst()
                    .map(SaleController::convertLineToSale)
                    .orElse(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Sale> getAllSales() {
        try {
            return Files.readAllLines(saleDB)
                    .stream()
                    .map(SaleController::convertLineToSale)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;    }

    private static Sale convertLineToSale(String line){
        Sale sale = new Sale();

        Map<Integer,Integer> map = new HashMap<>();
        String products = line.substring(line.indexOf('{')+1,line.indexOf('}'));
        String[] productList = products.split(",");
        for (String p: productList) {
            String[] var1 = p.split("=");
            map.put(Integer.parseInt(var1[0].trim()),Integer.parseInt(var1[1].trim()));
        }

        line = line.replace(products,"");
        String[] att = line.split(",");
        sale.setId(Integer.parseInt(att[0]));
        sale.setStoreName(att[1]);
        sale.setStaffId(Integer.parseInt(att[2]));
        sale.setClientId(Integer.parseInt(att[3]));
        sale.setProducts(map);
        sale.setTotalPrice(Double.parseDouble(att[5]));
        return sale;
    }
}
