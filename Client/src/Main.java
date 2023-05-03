import controller.ClientController;
import model.Chat;
import model.Client;
import org.json.simple.JSONObject;
import view.Application;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            ClientController clientController = new ClientController();
            Client client = new Client();

            Socket socket = new Socket("localhost", 3030);

            Application application = new Application(clientController, client, socket);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        try {
//            Client client = new Client("log1", "pas1");


//            clientController.addCar(socket, client.getLogin(), client.getPassword(), "BMW", "X5", "VINnum", "t999tt99");
//            clientController.getCarList(socket, client.getLogin(), client.getPassword());
//            clientController.getCar(socket, client.getLogin(), client.getPassword(), "a999aa99");
//            clientController.addRequest(socket, client.getLogin(), client.getPassword(), "description1", "BMW", "X5", "VinNumber1", "o333oo33");
//            socket.close();

//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

    }
}