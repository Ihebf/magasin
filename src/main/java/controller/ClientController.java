package controller;

import model.Client;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.nio.file.Path;

public class ClientController{

    private static final Path clientDB;

    static {
        clientDB = Paths.get(System.getProperty("user.dir") + "/src/main/resources/","clientDB.txt");
        if(!Files.exists(clientDB)) {
            try {
                Files.createFile(clientDB);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    // Test client controller
    public static void main(String[] args) {
        ClientController clientController = new ClientController();
        Client c = new Client("client1","client1@gmail.com","5100");
        Client c1 = new Client("client2","client2@gmail.com","5100");
        clientController.addClient(c);
        clientController.addClient(c1);

        System.out.println("get client by email: "+clientController.getClientByEmail("client2@gmail.com"));
        System.out.println("get client by id: "+clientController.getClientById(0));
        System.out.println("delete client: "+clientController.deleteClient(0));
        c1.setEmail("editedMail@gmail.com");
        clientController.editClient(c1);
        System.out.println("get all clients: "+clientController.getAllClients());

    }
    public boolean addClient(Client client) {
        if(getClientByEmail(client.getEmail())!=null){
            return false;
        }
        try {
            Files.writeString(clientDB, client.toString()+"\n", StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteClient(int id) {
        try {
            List<String> data = Files.readAllLines(clientDB)
                    .parallelStream()
                    .filter(l -> !(Integer.parseInt(l.split(",")[0]) == id))
                    .collect(Collectors.toList());
            Files.write(clientDB,data);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void editClient(Client client) {
        try {
            List<String> data = Files.readAllLines(clientDB);
            List<String> newData = new ArrayList<>();
            data.parallelStream()
                    .forEach(d -> {
                        if(Integer.parseInt(d.split(",")[0]) == client.getId()){
                           Client oldClient = convertLineToClient(d);
                           oldClient.setName(client.getName());
                           oldClient.setCarteNum(client.getCarteNum());
                           oldClient.setEmail(client.getEmail());
                           oldClient.setCodePostal(client.getCodePostal());
                           newData.add(oldClient.toString());
                        } else {
                            newData.add(d);
                        }
                    });
            Files.write(clientDB, newData, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Client getClientById(int id) {
        try {
            return Files.readAllLines(clientDB)
                    .parallelStream()
                    .filter(l -> Integer.parseInt(l.split(",")[0]) == id)
                    .findFirst()
                    .map(ClientController::convertLineToClient)
                    .orElse(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Client getClientByEmail(String email) {
        try {
            List<String> lines = Files.readAllLines(clientDB);
            if(lines.isEmpty())
                return null;
            return lines
                    .parallelStream()
                    .filter(l -> l.split(",")[3].equals(email))
                    .findFirst()
                    .map(ClientController::convertLineToClient)
                    .orElse(null);
        } catch (IOException e) {
            System.err.println("DB file doesn't exist");
        }
        return null;
    }

    public List<Client> getAllClients() {
        try {
            return Files.readAllLines(clientDB)
                    .stream()
                    .map(ClientController::convertLineToClient)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;    }

    private static Client convertLineToClient(String line){
        Client client = new Client();
        String[] att = line.split(",");
        client.setId(Integer.parseInt(att[0]));
        client.setName(att[1]);
        client.setCarteNum(Integer.parseInt(att[2]));
        client.setEmail(att[3]);
        client.setCodePostal(att[4]);
        return client;
    }
}
