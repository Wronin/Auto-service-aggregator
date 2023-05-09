package service;

import controller.ClientController;
import dao.AdminDao;
import model.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AdminService {
    public static class AdminServiceSingle {
        public static final AdminService INSTANCE = new AdminService();
    }
    public static AdminService getInstance() {
        return AdminService.AdminServiceSingle.INSTANCE;
    }
    public AdminService() {
    }

    public ArrayList<Request> getRequests(ServiceAdmin serviceAdmin){
        return AdminDao.getInstance().getRequests(serviceAdmin);
    }
    public ArrayList<Request> getAllAdminRequest(ServiceAdmin serviceAdmin) {
        return AdminDao.getInstance().getAllAdminRequest(serviceAdmin);
    }

    public Request getCurrentAdminRequest(int idRequest) {
        return AdminDao.getInstance().getCurrentAdminRequest(idRequest);
    }

    public void acceptRequestForAdmin(ServiceAdmin serviceAdmin, int id) {
        AdminDao.getInstance().acceptRequestForAdmin(serviceAdmin, id);
    }

    public void acceptRequestForAdminWithServices(ServiceAdmin serviceAdmin, int idRequest, ArrayList<Service> services) {
        AdminDao.getInstance().acceptRequestForAdminWithServices(serviceAdmin, idRequest, services);
    }

    public void changeStatusServiceRequest(ServiceAdmin serviceAdmin, int idRequest, int idService, String status) {
        AdminDao.getInstance().changeStatusServiceRequest(serviceAdmin, idRequest, idService, status);
    }

    public ArrayList<Chat> getChatsForAdmin(ServiceAdmin serviceAdmin) {
        return AdminDao.getInstance().getChatsForAdmin(serviceAdmin);
    }

    public Chat getCurrentChatForAdmin(ServiceAdmin serviceAdmin, int idChat) {
        Chat chat = AdminDao.getInstance().getCurrentChatForAdmin(serviceAdmin, idChat);
        chat.setMessages(AdminDao.getInstance().getMessagesByChatId(serviceAdmin, chat.getId()));
        return chat;
    }

    public void sendAdminMassage(ServiceAdmin serviceAdmin, int idChat, String message) {
        message =  String.format("Admin: %s", message);
        AdminDao.getInstance().sendAdminMassage(serviceAdmin, idChat, message);
    }


}
