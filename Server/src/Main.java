import controller.AdminController;
import controller.ClientController;
import org.json.simple.JsonObject;
import org.json.simple.Jsoner;

import javax.naming.ldap.SortKey;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {
    public static void main(String[] args) {
        ClientController clientController = new ClientController();
        AdminController adminController = new AdminController();
        clientController.getChatsForClient("log", "pas");

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
                                (Integer) jsonObject.get("idChat"),
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
                                    Integer.parseInt((String) jsonObject.get("id"))
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
                                    (Integer) jsonObject.get("id")
                            );
                    case "acceptRequestForAdmin" ->
                            adminController.acceptRequestForAdmin(
                                    (String) jsonObject.get("login"),
                                    (String) jsonObject.get("password"),
                                    (Integer) jsonObject.get("id")
                            );
                    case "getAllClientRequest" ->
                            clientController.getAllClientRequest(
                                    socket,
                                    (String) jsonObject.get("login"),
                                    (String) jsonObject.get("password")
                            );
                    case "getAnswerAutoService" ->
                            clientController.getAnswerAutoService(
                                    socket, (String) jsonObject.get("login"),
                                    (String) jsonObject.get("password")
                            );
                    case "getCarServiceByName" ->
                            clientController.getCarServiceByName(
                                    socket,
                                    (String) jsonObject.get("name")
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