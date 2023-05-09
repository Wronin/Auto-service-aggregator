package network;

import controller.AdminController;
import controller.ClientController;
import model.Client;
import model.Service;
import model.Status;
import org.json.simple.JsonObject;
import org.json.simple.Jsoner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.net.Socket;
import java.util.ArrayList;

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
                    case "addCar" -> ClientController.getInstance().addCar(
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password"),
                            (String) jsonObject.get("brand"),
                            (String) jsonObject.get("model"),
                            (String) jsonObject.get("VINNumber"),
                            (String) jsonObject.get("regNumber")
                    );
                    case "deleteCar" -> ClientController.getInstance().deleteCar(
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password"),
                            (String) jsonObject.get("brand"),
                            (String) jsonObject.get("model"),
                            (String) jsonObject.get("VINNumber"),
                            (String) jsonObject.get("regNumber")
                    );
                    case "updateCarInformation" -> ClientController.getInstance().updateCarInformation(
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password"),
                            (String) jsonObject.get("brand"),
                            (String) jsonObject.get("model"),
                            (String) jsonObject.get("VINNumber"),
                            (String) jsonObject.get("regNumber"),
                            (String) jsonObject.get("newBrand"),
                            (String) jsonObject.get("newModel"),
                            (String) jsonObject.get("newVINNumber"),
                            (String) jsonObject.get("newRegNumber")

                    );
                    case "getCarList" -> ClientController.getInstance().getCarList(
                            socket,
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password")
                    );
                    case "getCarNumbers" -> ClientController.getInstance().getCarNumbers(
                            socket,
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password")
                    );
                    case "getCar" -> ClientController.getInstance().getCar(
                            socket,
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password"),
                            (String) jsonObject.get("regNumber")
                    );
                    case "addRequest" -> ClientController.getInstance().addRequest(
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password"),
                            (String) jsonObject.get("description"),
                            (String) jsonObject.get("brand"),
                            (String) jsonObject.get("model"),
                            (String) jsonObject.get("VINNumber"),
                            (String) jsonObject.get("regNumber")
                    );
                    case "addRequestWithServices" -> ClientController.getInstance().addRequestWithServices(
                            bufferedReader,
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password"),
                            (String) jsonObject.get("description"),
                            (String) jsonObject.get("brand"),
                            (String) jsonObject.get("model"),
                            (String) jsonObject.get("VINNumber"),
                            (String) jsonObject.get("regNumber")
                    );
                    case "sendClientMessage" -> ClientController.getInstance().sendClientMessage(
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password"),
                            jsonObject.getInteger("idChat"),
                            (String) jsonObject.get("message")
                    );
                    case "getChatsForClient" -> ClientController.getInstance().getChatsForClient(
                            socket,
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password")
                    );
                    case "getCurrentChatForClient" -> ClientController.getInstance().getCurrentChatForClient(
                            socket,
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password"),
                            jsonObject.getInteger("idChat")
                    );
                    case "getAllClientRequest" -> ClientController.getInstance().getAllClientRequest(
                            socket,
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password")
                    );
                    case "getAnswerAutoService" -> ClientController.getInstance().getAnswerAutoService(
                            socket,
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password")
                    );
                    case "getCarServiceByName" -> ClientController.getInstance().getCarServiceById(
                            socket,
                            jsonObject.getInteger("id")
                    );
                    case "acceptRequestForClient" -> ClientController.getInstance().acceptRequestForClient(
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password"),
                            jsonObject.getInteger("idAnswer")
                    );
                    case "getRequests" -> AdminController.getInstance().getRequests(
                            socket,
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password")
                    );
                    case "getAllAdminRequest" -> AdminController.getInstance().getAllAdminRequest(
                            socket,
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password")
                    );
                    case "getCurrentAdminRequest" -> AdminController.getInstance().getCurrentAdminRequest(
                            socket,
                            jsonObject.getInteger("id")
                    );
                    case "acceptRequestForAdmin" -> AdminController.getInstance().acceptRequestForAdmin(
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password"),
                            jsonObject.getInteger("id")
                    );
                    case "acceptRequestForAdminWithServices" ->
                            AdminController.getInstance().acceptRequestForAdminWithServices(
                                    bufferedReader,
                                    (String) jsonObject.get("login"),
                                    (String) jsonObject.get("password"),
                                    jsonObject.getInteger("idRequest")
                            );
                    case "changeStatusServiceRequest" -> AdminController.getInstance().changeStatusServiceRequest(
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password"),
                            jsonObject.getInteger("idRequest"),
                            jsonObject.getInteger("idService"),
                            (String) jsonObject.get("status")
                    );
                    case "getChatsForAdmin" -> AdminController.getInstance().getChatsForAdmin(
                            socket,
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password")
                    );
                    case "getCurrentChatForAdmin" -> AdminController.getInstance().getCurrentChatForAdmin(
                            socket,
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password"),
                            jsonObject.getInteger("idChat")
                    );
                    case "sendAdminMassage" -> AdminController.getInstance().sendAdminMassage(
                            (String) jsonObject.get("login"),
                            (String) jsonObject.get("password"),
                            jsonObject.getInteger("idChat"),
                            (String) jsonObject.get("message")
                    );
                    default -> throw new IllegalStateException("Unexpected value: " + jsonObject.get("func"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Service> getServicesFromClientRequest(JsonObject jsonObject) {
        ArrayList<Service> services = new ArrayList<>();

        for (int i = 0; i < jsonObject.getInteger("service size"); i++) {
            services.add(new Service(jsonObject.getInteger("id"), jsonObject.getString("name"), jsonObject.getString("description")));
        }

        return services;
    }
}
