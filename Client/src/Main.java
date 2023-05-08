import controller.ClientController;
import model.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            ClientController clientController = new ClientController();
            Client client = new Client();

            Socket socket = new Socket("localhost", 3030);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("log");
            printWriter.println("pas");

            ArrayList<Service> services = new ArrayList<>();
            services.add(new Service(1));
            services.add(new Service(2));
//            clientController.addCar(socket, "log", "pas", "BMW", "X1", "vin", "regNum");
//            clientController.updateCarInformation(socket, "log", "pas", "BMW", "X1", "vin", "regNum", "Audi", "Q1", "newVin", "newReg");
//            clientController.deleteCar(socket, "log", "pas", "Audi", "Q1", "newVin", "newReg");
//
//            clientController.addCar(socket, "log", "pas", "BMW", "X1", "vin", "regNum");
//            ArrayList<Car> cars = clientController.getCarList(socket, "log", "pas");
//            ArrayList<String> carsNumbers = clientController.getCarNumbers(socket, "log", "pas");
//            Car car = clientController.getCar(socket, "log", "pas", "regNum");
//            clientController.addRequest(socket, "log", "pas", "description", "BMW", "X1", "vin", "regNum");
//            clientController.addRequestWithServices(socket, "log", "pas", "BMW", "X1", "123qwe", "k111kk11", services);
//            ArrayList<Chat> chats = clientController.getChatsForClient(socket, "log", "pas");
//            Chat chat = clientController.getCurrentChatForClient(socket, "log", "pas", 1);
//            clientController.sendClientMessage(socket, "log", "pas", 1, "test from client");
//            ArrayList<RequestForClient> requestsForClient = clientController.getAllClientRequest(socket, "log", "pas");
//            ArrayList<AnswerAutoService> answersAutoService = clientController.getAnswerAutoService(socket, "log", "pas");
//            CarService carService = clientController.getCarServiceById(socket, 1);
//            clientController.acceptRequest(socket, "log", "pas", 3);

            System.out.println("done");
            //            Application application = new Application(clientController, client, socket);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}