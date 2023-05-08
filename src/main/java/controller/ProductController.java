package controller;

import model.Product;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductController {
    private static final Path productDB;
    private static final List<String> PRODUCT_TYPES = new ArrayList<>();
    static {
        productDB = Paths.get(System.getProperty("user.dir") + "/src/main/resources/","productDB.txt");
        if(!Files.exists(productDB)) {
            try {
                Files.createFile(productDB);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        PRODUCT_TYPES.add("ALIMENTS");
        PRODUCT_TYPES.add("MEDECIENE");
        PRODUCT_TYPES.add("APPAREIL_ELECTRONIQUE");
        PRODUCT_TYPES.add("AUTRE");
    }
    // Test Product controller
    public static void main(String[] args) {
        ProductController ProductController = new ProductController();
//        Product c = new Product("Product1","Product1@gmail.com","5100");
//        Product c1 = new Product("Product2","Product2@gmail.com","5100");
//        ProductController.addProduct(c);
//        ProductController.addProduct(c1);
//
//        System.out.println("get Product by email: "+ProductController.getProductByName("Product2@gmail.com"));
//        System.out.println("get Product by id: "+ProductController.getProductById(0));
//        System.out.println("delete Product: "+ProductController.deleteProduct(0));
//        c1.setEmail("editedMail@gmail.com");
//        ProductController.editProduct(c1);
//        System.out.println("get all Products: "+ProductController.getAllProducts());

    }
    public boolean addProduct(Product product) {
        if(getProductByName(product.getName())!=null){
            return false;
        } else if(!PRODUCT_TYPES.contains(product.getType())){
            System.err.println("product type should be one of: "+PRODUCT_TYPES);
            return false;
        }
        try {
            Files.writeString(productDB, product.toString()+"\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteProduct(int id) {
        try {
            List<String> data = Files.readAllLines(productDB)
                    .parallelStream()
                    .filter(l -> !(Integer.parseInt(l.split(",")[0]) == id))
                    .collect(Collectors.toList());
            Files.write(productDB,data);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void editProduct(Product product) {
        try {
            List<String> data = Files.readAllLines(productDB);
            List<String> newData = new ArrayList<>();
            data.parallelStream()
                    .forEach(d -> {
                        if(Integer.parseInt(d.split(",")[0]) == product.getId()){
                            Product oldProduct = convertLineToProduct(d);
                            oldProduct.setId(product.getId());
                            oldProduct.setName(product.getName());
                            oldProduct.setDescription(product.getDescription());
                            oldProduct.setType(product.getType());
                            oldProduct.setPrice(product.getPrice());
                            oldProduct.setCountry(product.getCountry());
                            oldProduct.setTVA(product.getTVA());
                            oldProduct.setQuantity(product.getQuantity());
                            oldProduct.setSupplierName(product.getSupplierName());
                            newData.add(oldProduct.toString());
                        } else {
                            newData.add(d);
                        }
                    });
            Files.write(productDB, newData, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Product getProductById(int id) {
        try {
            return Files.readAllLines(productDB)
                    .parallelStream()
                    .filter(l -> Integer.parseInt(l.split(",")[0]) == id)
                    .findFirst()
                    .map(ProductController::convertLineToProduct)
                    .orElse(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Product getProductByName(String name) {
        try {
            return Files.readAllLines(productDB)
                    .parallelStream()
                    .filter(l -> l.split(",")[1].equals(name))
                    .findFirst()
                    .map(ProductController::convertLineToProduct)
                    .orElse(null);
        } catch (IOException e) {
            System.err.println("DB file doesn't exist");
        }
        return null;
    }

    public List<Product> matchProductByName(String name) {
        try {
            return Files.readAllLines(productDB)
                    .parallelStream()
                    .filter(l -> l.split(",")[1].contains(name))
                    .map(ProductController::convertLineToProduct)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("DB file doesn't exist");
        }
        return null;
    }

    public List<Product> getAllProducts() {
        try {
            return Files.readAllLines(productDB)
                    .stream()
                    .map(ProductController::convertLineToProduct)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;    }

    private static Product convertLineToProduct(String line){
        Product product = new Product();
        String[] att = line.split(",");
        product.setId(Integer.parseInt(att[0]));
        product.setName(att[1]);
        product.setDescription(att[2]);
        product.setType(att[3]);
        product.setPrice(Double.parseDouble(att[4]));
        product.setCountry(att[5]);
        product.setTVA(Double.parseDouble(att[6]));
        product.setQuantity(Integer.parseInt(att[7]));
        product.setSupplierName(att[8]);
        return product;
    }
}
