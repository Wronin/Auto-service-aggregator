package service;

import dao.AdminDao;
import model.Request;
import model.ServiceAdmin;

import java.util.ArrayList;

public class AdminService {
    private AdminDao adminDao = new AdminDao();
    public AdminService() {
    }

    public ArrayList<Request> getAllAdminRequest(ServiceAdmin serviceAdmin) {
        return adminDao.getAllAdminRequest(serviceAdmin);
    }

    public Request getCurrentAdminRequest(ServiceAdmin serviceAdmin, int id) {
        ArrayList<Request> requests = adminDao.getAllAdminRequest(serviceAdmin);

        for (var request : requests) {
            if (request.getId() == id)
                return request;
        }
        return new Request();
    }

    public void acceptRequestForAdmin(ServiceAdmin serviceAdmin, int id) {
        adminDao.acceptRequestForAdmin(serviceAdmin, id);
    }

    public void createChat(ServiceAdmin serviceAdmin, Request request) {
        adminDao.createChat(serviceAdmin, request);
    }

}