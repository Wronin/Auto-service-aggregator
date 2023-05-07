package network;

import controller.AdminController;
import controller.ClientController;
import model.Client;
import org.json.simple.JsonObject;
import org.json.simple.Jsoner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Network extends Thread {
    private final Client client;
    private final Socket socket;
    private final BufferedReader bufferedReader;
    private final ClientController clientController;
    private final AdminController adminController;

    public Network(Client client, Socket socket, BufferedReader bufferedReader, ClientController clientController, AdminController adminController) {
        this.client = client;
        this.socket = socket;
        this.bufferedReader = bufferedReader;
        this.clientController = clientController;
        this.adminController = adminController;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String content = bufferedReader.readLine();

                File file = new File(System.getProperty("user.dir"), "file.json");
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(content);
                fileWriter.close();

                System.out.println(content);

                JsonObject jsonObject = (JsonObject) Jsoner.deserialize(content);
                System.out.println(jsonObject.get("func"));

                switch (jsonObject.get("func").toString()) {
                    case "addCar" -> clientController.addCar(
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password"),
                            (String) jsonObject.get("brand"),
                            (String) jsonObject.get("model"),
                            (String) jsonObject.get("VINNumber"),
                            (String) jsonObject.get("regNumber")
                    );
                    case "getCarList" -> clientController.getCarList(
                            socket,
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password")
                    );
                    case "getCarNumbers" -> clientController.getCarNumbers(
                            socket,
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password")
                    );
                    case "getCar" -> clientController.getCar(
                            socket,
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password"),
                            (String) jsonObject.get("regNumber")
                    );
                    case "addRequest" -> clientController.addRequest(
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password"),
                            (String) jsonObject.get("description"),
                            (String) jsonObject.get("brand"),
                            (String) jsonObject.get("model"),
                            (String) jsonObject.get("VINNumber"),
                            (String) jsonObject.get("regNumber")
                    );
                    case "sendClientMassage" -> clientController.sendClientMassage(
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password"),
                            jsonObject.getInteger("idChat"),
                            (String) jsonObject.get("message")
                    );
                    case "getChatsForClient" -> clientController.getChatsForClient(
                            socket,
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password")
                    );
                    case "getAllAdminRequest" -> adminController.getAllAdminRequest(
                            socket,
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password")
                    );
                    case "getCurrentRequest" -> adminController.getCurrentAdminRequest(
                            socket,
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password"),
                            jsonObject.getInteger("id")
                    );
                    case "acceptRequestForAdmin" -> adminController.acceptRequestForAdmin(
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password"),
                            jsonObject.getInteger("id")
                    );
                    case "getAllClientRequest" -> clientController.getAllClientRequest(
                            socket,
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password")
                    );
                    case "getAnswerAutoService" -> clientController.getAnswerAutoService(
                            socket,
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password")
                    );
                    case "getCarServiceByName" -> clientController.getCarServiceById(
                            socket,
                            jsonObject.getInteger("id")
                    );
                    case "acceptRequestForClient" -> clientController.acceptRequestForClient(
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password"),
                            jsonObject.getInteger("idAnswer")
                    );
                    default -> throw new IllegalStateException("Unexpected value: " + jsonObject.get("func"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
