import controller.AdminController;
import model.ServiceAdmin;
import view.Application;

import java.io.IOException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {

        try {
            AdminController adminController = new AdminController();
            ServiceAdmin serviceAdmin = new ServiceAdmin();

            Socket socket = new Socket("localhost", 3030);
            Application application = new Application(adminController, serviceAdmin, socket);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}