import controller.ClientController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Main extends Application {
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view/hello-view.fxml"));
            Scene scene = new Scene(loader.load(), 850, 500);
            stage.setTitle("Aggregator");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args)  {
//            clientController.addCar(socket, "log", "pas", "BMW", "X1", "vin", "regNum");
//            clientController.updateCarInformation(socket, "log", "pas", "BMW", "X1", "vin", "regNum", "Audi", "Q1", "newVin", "newReg");
//            clientController.deleteCar(socket, "log", "pas", "Audi", "Q1", "newVin", "newReg");
//
//            clientController.addCar(socket, "log", "pas", "BMW", "X1", "vin", "regNum");
//            ArrayList<Car> cars = clientController.getCarList(socket, "log", "pas");
//            ArrayList<String> carsNumbers = clientController.getCarNumbers(socket, "log", "pas");
//            Car car = clientController.getCar(socket, "log", "pas", "regNum");
//            clientController.addRequest(socket, "log", "pas", "description", "BMW", "X1", "vin", "regNum");
//            clientController.addRequestWithServices(socket, "log", "pas", "need backup", "BMW", "X1", "123qwe", "k111kk11", services);
//            ArrayList<Chat> chats = clientController.getChatsForClient(socket, "log", "pas");
//            Chat chat = clientController.getCurrentChatForClient(socket, "log", "pas", 1);
//            clientController.sendClientMessage(socket, "log", "pas", 1, "test from client");
//            ArrayList<RequestForClient> requestsForClient = clientController.getAllClientRequest(socket, "log", "pas");
//            ArrayList<AnswerAutoService> answersAutoService = clientController.getAnswerAutoService(socket, "log", "pas");
//            CarService carService = clientController.getCarServiceById(socket, 1);
//            clientController.acceptRequest(socket, "log", "pas", 3);
        launch();


    }
}