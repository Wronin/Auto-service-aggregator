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
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("log");
            printWriter.println("pas");
            clientController.addCar(socket, "log", "pas", "BMW", "X1", "vin", "regNum");

//            Application application = new Application(clientController, client, socket);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}