package service;

import controller.ClientController;
import dao.AdminDao;
import model.Chat;
import model.Request;
import model.Service;
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

    public void acceptRequestForAdminWithServices(ServiceAdmin serviceAdmin, int idRequest, ArrayList<Service> services) {
        adminDao.acceptRequestForAdminWithServices(serviceAdmin, idRequest, services);
    }

        public ArrayList<Chat> getChatsForAdmin(ServiceAdmin serviceAdmin) {
        return adminDao.getChatsForAdmin(serviceAdmin);
    }

    public Chat getCurrentChatForAdmin(ServiceAdmin serviceAdmin, int idChat) {
        Chat chat = adminDao.getCurrentChatForAdmin(serviceAdmin, idChat);
        chat.setMessages(adminDao.getMessagesByChatId(serviceAdmin, idChat));
        return chat;
    }

    public void sendAdminMassage(ServiceAdmin serviceAdmin, int idChat, String message) {
        message =  String.format("Admin: %s", message);
        adminDao.sendAdminMassage(serviceAdmin, idChat, message);
    }


}
