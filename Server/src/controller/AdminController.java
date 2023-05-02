package controller;

import model.*;
import service.AdminService;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class AdminController {
    private AdminService adminService = new AdminService();
    public AdminController() {
    }

    public void getAllAdminRequest(Socket socket, String login, String password) {
        ServiceAdmin serviceAdmin = new ServiceAdmin(login, password);
        ArrayList<Request> requests = adminService.getAllAdminRequest(serviceAdmin);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());

            printWriter.println(requests.size());
            printWriter.flush();
            Car car;
            for (var request : requests) {
                printWriter.println(request.getId());
                printWriter.flush();

                printWriter.println(request.getDescription());
                printWriter.flush();

                printWriter.println(request.getCar().getVINNumber());
                printWriter.flush();

                printWriter.println(request.getCar().getRegNumber());
                printWriter.flush();

                printWriter.println(request.getCar().getBrand());
                printWriter.flush();

                printWriter.println(request.getCar().getModel());
                printWriter.flush();

                printWriter.println(request.getStatus());
                printWriter.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Request getCurrentAdminRequest(String login, String password, int id) {
        ServiceAdmin serviceAdmin = new ServiceAdmin(login, password);
        return adminService.getCurrentAdminRequest(serviceAdmin, id);
    }

    public void acceptRequestForAdmin(String login, String password, int id) {
        ServiceAdmin serviceAdmin = new ServiceAdmin(login, password);
        adminService.acceptRequestForAdmin(serviceAdmin, id);
    }

    public void acceptRequestForAdminWithServices(String login, String password, int idRequest, ArrayList<Service> services) {
        ServiceAdmin serviceAdmin = new ServiceAdmin(login, password);
        adminService.acceptRequestForAdminWithServices(serviceAdmin, idRequest, services);
    }

    //todo make a controller's functions send a date
    public void getChatsForAdmin(Socket socket, String login, String password) {
        ServiceAdmin serviceAdmin = new ServiceAdmin(login, password);
        ArrayList<Chat> chats = adminService.getChatsForAdmin(serviceAdmin);
    }

    public void sendAdminMassage(String login, String password, int idChat, String message) {
        ServiceAdmin serviceAdmin = new ServiceAdmin(login, password);
        adminService.sendAdminMassage(serviceAdmin, idChat, message);
    }
}
