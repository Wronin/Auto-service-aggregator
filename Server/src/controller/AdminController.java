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

    public void acceptRequest(String login, String password, int id) {
        ServiceAdmin serviceAdmin = new ServiceAdmin(login, password);
        adminService.acceptRequest(serviceAdmin, id);
    }
    public void createChat(String login, String password, String description, String brand, String model, String VINNumber, String regNumber, String status, int id) {
        ServiceAdmin serviceAdmin = new ServiceAdmin(login, password);
        Car car = new Car(VINNumber, regNumber, brand, model);
        Request request = new Request(id, description, car, Status.SEARCH);
        adminService.createChat(serviceAdmin, request);
    }
}
