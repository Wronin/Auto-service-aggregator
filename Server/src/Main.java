import controller.AdminController;
import controller.ClientController;
import dao.NetworkDao;
import model.Client;
import network.Network;
import org.json.simple.JsonObject;
import org.json.simple.Jsoner;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {
    public static void main(String[] args) {
        ClientController clientController = new ClientController();
        AdminController adminController = new AdminController();

        try (ServerSocket serverSocket = new ServerSocket(3030)) {
            while (true) {
                Socket socket = serverSocket.accept();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                Client client = new Client(bufferedReader.readLine(), bufferedReader.readLine());
                Network network = new Network(client, socket, bufferedReader, printWriter, clientController, adminController);
                NetworkDao.getInstance().addServerThread(network);
                network.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}