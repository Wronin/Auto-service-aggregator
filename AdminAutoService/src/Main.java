import controller.AdminController;
import model.*;
import view.Application;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        try {
            AdminController adminController = new AdminController();
            ServiceAdmin serviceAdmin = new ServiceAdmin();

            Socket socket = new Socket("localhost", 3030);

            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println("log");
            printWriter.println("pas");

//            ArrayList<Request> requests = adminController.getRequests(socket, "log1", "pas1");
//            ArrayList<Request> requests1 = adminController.getAllAdminRequest(socket, "log1", "pas1");
//            Request request = adminController.getCurrentAdminRequest(socket, "log1", "pas1", 1);
//            adminController.acceptRequestForAdmin(socket, "log1", "pas1", 6);
            ArrayList<Service> services = new ArrayList<>();
            services.add(new Service(1, "name", "desc"));
            services.add(new Service(2, "name1", "desc1"));
//            adminController.acceptRequestForAdminWithServices(socket, "log1", "pas1", 6, services);
//            ArrayList<Chat> chats = adminController.getChatsForAdmin(socket, "log1", "pas1");
//            Chat chat = adminController.getCurrentChatForAdmin(socket, "log1", "pas1", 1);
            adminController.sendAdminMassage(socket, "log1", "pas1", 1, "message from admin test");
            adminController.changeStatusServiceRequest(socket, "log1", "pas1", 14, 2, "Waiting");
            System.out.println("done");

//            Application application = new Application(adminController, serviceAdmin, socket);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}