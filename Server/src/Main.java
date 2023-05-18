import controller.AdminController;
import controller.ClientController;
import controller.NetworkController;
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

        try (ServerSocket serverSocket = new ServerSocket(3031)) {
            while (true) {
                Socket socket = serverSocket.accept();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
                Client client;
                boolean authorization = false;

                while (!authorization) {
                    String action = bufferedReader.readLine();
                    client = new Client(bufferedReader.readLine(), bufferedReader.readLine());
                    if (action.equals("Client authorization")) {
                        if (NetworkController.getInstance().authorization(client)) {
                            Network network = new Network(client, socket, bufferedReader, clientController, adminController);
                            NetworkDao.getInstance().addServerThread(network);
                            network.start();
                            authorization = true;
                            printWriter.println("Yes");
                        } else {
                            printWriter.println("error");
                        }
                    } else if (action.equals("Client registration")) {
                        if (NetworkController.getInstance().registration(client)) {
                            Network network = new Network(client, socket, bufferedReader, clientController, adminController);
                            NetworkDao.getInstance().addServerThread(network);
                            network.start();
                            authorization = true;
                            printWriter.println("Yes");
                        } else {
                            printWriter.println("error");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}