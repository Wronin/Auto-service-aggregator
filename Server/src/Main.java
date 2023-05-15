import controller.AdminController;
import controller.ClientController;
import model.*;
import network.Network;
import dao.NetworkDao;

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
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                printWriter.println("Yes");
                Network network = new Network(client, socket, bufferedReader, clientController, adminController);
                NetworkDao.getInstance().addServerThread(network);
                network.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}