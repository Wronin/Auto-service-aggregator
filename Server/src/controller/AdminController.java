package controller;

import dao.AdminDao;
import model.*;
import service.AdminService;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class AdminController {
    public static class AdminControllerSingle {
        public static final AdminController INSTANCE = new AdminController();
    }
    public static AdminController getInstance() {
        return AdminController.AdminControllerSingle.INSTANCE;
    }
    public AdminController() {
    }

    public void getRequests(Socket socket, String login, String password) {
        ServiceAdmin serviceAdmin = new ServiceAdmin(login, password);
        ArrayList<Request> requests = AdminService.getInstance().getRequests(serviceAdmin);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

            printWriter.println(requests.size());
            for (Request request : requests) {
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

    public void getAllAdminRequest(Socket socket, String login, String password) {
        ServiceAdmin serviceAdmin = new ServiceAdmin(login, password);
        ArrayList<Request> requests = AdminService.getInstance().getAllAdminRequest(serviceAdmin);

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

    public void getCurrentAdminRequest(Socket socket, int idRequest) {
        Request request = AdminService.getInstance().getCurrentAdminRequest(idRequest);

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
        AdminService.getInstance().acceptRequestForAdmin(serviceAdmin, id);
    }

    public void acceptRequestForAdminWithServices(BufferedReader bufferedReader, String login, String password, int idRequest) {
        ServiceAdmin serviceAdmin = new ServiceAdmin(login, password);
        ArrayList<Service> services = new ArrayList<>();

        try {
            int size = Integer.parseInt(bufferedReader.readLine());
            for (int i = 0; i < size; i++) {
                services.add(new Service(Integer.parseInt(bufferedReader.readLine()), bufferedReader.readLine(), bufferedReader.readLine()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        AdminService.getInstance().acceptRequestForAdminWithServices(serviceAdmin, idRequest, services);
    }

    public void changeStatusServiceRequest(String login, String password, int idRequest, int idService, Status status) {
        ServiceAdmin serviceAdmin = new ServiceAdmin(login, password);
        AdminDao.getInstance().changeStatusServiceRequest(serviceAdmin, idRequest, idService, status);
    }

    public void getChatsForAdmin(Socket socket, String login, String password) {
        ServiceAdmin serviceAdmin = new ServiceAdmin(login, password);
        ArrayList<Chat> chats = AdminService.getInstance().getChatsForAdmin(serviceAdmin);

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
        Chat chat = AdminService.getInstance().getCurrentChatForAdmin(serviceAdmin, idChat);

        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

            printWriter.println(chat.getId());
            printWriter.println(chat.getCar().getRegNumber());
            printWriter.println(chat.getCar().getVINNumber());
            printWriter.println(chat.getCar().getModel());
            printWriter.println(chat.getCar().getBrand());
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
        AdminService.getInstance().sendAdminMassage(serviceAdmin, idChat, message);
    }
}
