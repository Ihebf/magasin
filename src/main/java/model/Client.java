package model;

import java.util.Random;

public class Client {

    private Integer id;
    private String name;
    private int carteNum;
    private String email;
    private String codePostal;

    private static int count = 0;

    public Client(String name, String email, String codePostal) {
        this.id = count++;
        this.name = name;
        Random random = new Random();
        this.carteNum = random.nextInt(100000000)+100000000;
        this.email = email;
        this.codePostal = codePostal;
    }

    @Override
    public String toString() {
        return  id +
                "," + name +
                "," + carteNum +
                "," + email +
                "," + codePostal;
    }

    public Client() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCarteNum() {
        return carteNum;
    }

    public void setCarteNum(int carteNum) {
        this.carteNum = carteNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }
}
