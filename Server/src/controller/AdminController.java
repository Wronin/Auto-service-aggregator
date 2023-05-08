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
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

            printWriter.println(requests.size());
            for (var request : requests) {
                printWriter.println(request.getId());
                printWriter.println(request.getDescription());
                printWriter.println(request.getCar().getVINNumber());
                printWriter.println(request.getCar().getRegNumber());
                printWriter.println(request.getCar().getBrand());
                printWriter.println(request.getCar().getModel());
                printWriter.println(request.getStatus());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCurrentAdminRequest(Socket socket, String login, String password, int id) {
        ServiceAdmin serviceAdmin = new ServiceAdmin(login, password);
        Request request = adminService.getCurrentAdminRequest(serviceAdmin, id);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

            printWriter.println(request.getId());
            printWriter.println(request.getDescription());
            printWriter.println(request.getCar().getVINNumber());
            printWriter.println(request.getCar().getRegNumber());
            printWriter.println(request.getCar().getBrand());
            printWriter.println(request.getCar().getModel());
            printWriter.println(request.getStatus());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void acceptRequestForAdmin(String login, String password, int id) {
        ServiceAdmin serviceAdmin = new ServiceAdmin(login, password);
        adminService.acceptRequestForAdmin(serviceAdmin, id);
    }

    public void acceptRequestForAdminWithServices(String login, String password, int idRequest, ArrayList<Service> services) {
        ServiceAdmin serviceAdmin = new ServiceAdmin(login, password);
        adminService.acceptRequestForAdminWithServices(serviceAdmin, idRequest, services);
    }

    public void getChatsForAdmin(Socket socket, String login, String password) {
        ServiceAdmin serviceAdmin = new ServiceAdmin(login, password);
        ArrayList<Chat> chats = adminService.getChatsForAdmin(serviceAdmin);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

            printWriter.println(chats.size());
            for (Chat chat : chats) {
                printWriter.println(chat.getId());
                printWriter.println(chat.getCar().getVINNumber());
                printWriter.println(chat.getCar().getRegNumber());
                printWriter.println(chat.getCar().getModel());
                printWriter.println(chat.getCar().getBrand());
                printWriter.println(chat.getCarServiceName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCurrentChatForAdmin(Socket socket, String login, String password, int idChat) {
        ServiceAdmin serviceAdmin = new ServiceAdmin(login, password);
        Chat chat = adminService.getCurrentChatForAdmin(serviceAdmin, idChat);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

            printWriter.println(chat.getId());
            printWriter.println(chat.getCar().getRegNumber());
            printWriter.println(chat.getCarServiceName());
            printWriter.println(chat.getMessages().size());

            for (Message message : chat.getMessages()) {
                printWriter.println(message.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendAdminMassage(String login, String password, int idChat, String message) {
        ServiceAdmin serviceAdmin = new ServiceAdmin(login, password);
        adminService.sendAdminMassage(serviceAdmin, idChat, message);
    }
}
