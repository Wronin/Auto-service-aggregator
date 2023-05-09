import controller.AdminController;
import controller.ClientController;
import dao.AdminDao;
import dao.ClientDao;
import dao.NetworkDao;
import model.Chat;
import model.Client;
import model.ServiceAdmin;
import model.Status;
import network.Network;
import service.AdminService;
import service.ClientService;

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
                Client client = new Client(bufferedReader.readLine(), bufferedReader.readLine());
                Network network = new Network(client, socket, bufferedReader, clientController, adminController);
                NetworkDao.getInstance().addServerThread(network);
                network.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}