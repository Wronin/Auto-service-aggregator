import controller.AdminController;
import controller.ClientController;
import model.Service;
import org.json.simple.JsonObject;
import org.json.simple.Jsoner;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) {
        ClientController clientController = new ClientController();
        AdminController adminController = new AdminController();

        ArrayList<Service> services = new ArrayList<>();
        services.add(new Service(1, "Замена масла", "Замена вашего масла на наше"));
        services.add(new Service(2, "Покраска автомобиля", "Любой слжоности"));
        clientController.addRequestWithServices("log", "pas", "need help Steam", "BMW", "X1", "123qwe", "k111kk11", services);

        try {
            ServerSocket serverSocket = new ServerSocket(3030);
            Socket socket = serverSocket.accept();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

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
                    case "AddCar" -> clientController.addCar(
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password"),
                            (String) jsonObject.get("brand"),
                            (String) jsonObject.get("model"),
                            (String) jsonObject.get("VINNumber"),
                            (String) jsonObject.get("regNumber")
                    );
                    case "getCarList" ->
                            clientController.getCarList(
                                    socket,
                                    (String) jsonObject.get("login"),
                                    (String) jsonObject.get("password")
                            );
                    case "getCarNumbers" ->
                            clientController.getCarNumbers(
                                    socket,
                                    (String) jsonObject.get("login"),
                                    (String) jsonObject.get("password")
                            );
                    case "getCar" ->
                            clientController.getCar(
                                    socket,
                                    (String) jsonObject.get("login"),
                                    (String) jsonObject.get("password"),
                                    (String) jsonObject.get("regNumber")
                            );
                    case "addRequest" ->
                            clientController.addRequest(
                                    (String) jsonObject.get("login"),
                                    (String) jsonObject.get("password"),
                                    (String) jsonObject.get("description"),
                                    (String) jsonObject.get("brand"),
                                    (String) jsonObject.get("model"),
                                    (String) jsonObject.get("VINNumber"),
                                    (String) jsonObject.get("regNumber")
                            );
                    case "sendClientMassage" ->
                        clientController.sendClientMassage(
                                (String) jsonObject.get("login"),
                                (String) jsonObject.get("password"),
                                jsonObject.getInteger("idChat"),
                                (String) jsonObject.get("message")
                        );
                    case "getAllAdminRequest" ->
                            adminController.getAllAdminRequest(
                                    socket,
                                    (String) jsonObject.get("login"),
                                    (String) jsonObject.get("password")
                            );
                    case "getCurrentRequest" ->
                            adminController.getCurrentAdminRequest(
                                    (String) jsonObject.get("login"),
                                    (String) jsonObject.get("password"),
                                    jsonObject.getInteger("id")
                            );
                    case "createChat" ->
                            adminController.createChat(
                                    (String) jsonObject.get("login"),
                                    (String) jsonObject.get("password"),
                                    (String) jsonObject.get("description"),
                                    (String) jsonObject.get("brand"),
                                    (String) jsonObject.get("model"),
                                    (String) jsonObject.get("VINNumber"),
                                    (String) jsonObject.get("regNumber"),
                                    (String) jsonObject.get("status"),
                                    jsonObject.getInteger("id")
                            );
                    case "acceptRequestForAdmin" ->
                            adminController.acceptRequestForAdmin(
                                    (String) jsonObject.get("login"),
                                    (String) jsonObject.get("password"),
                                    jsonObject.getInteger("id")
                            );
                    case "getAllClientRequest" ->
                            clientController.getAllClientRequest(
                                    socket,
                                    (String) jsonObject.get("login"),
                                    (String) jsonObject.get("password")
                            );
                    case "getAnswerAutoService" ->
                            clientController.getAnswerAutoService(
                                    socket,
                                    (String) jsonObject.get("login"),
                                    (String) jsonObject.get("password")
                            );
                    case "getCarServiceByName" ->
                            clientController.getCarServiceById(
                                    socket,
                                    jsonObject.getInteger("id")
                            );
                    case "acceptRequestForClient" ->
                            clientController.acceptRequestForClient(
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