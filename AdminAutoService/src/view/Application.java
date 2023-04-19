package view;

import controller.AdminController;
import model.ServiceAdmin;

import javax.swing.*;
import java.awt.*;
import java.net.Socket;

public class Application extends JFrame{
    private final int WIDTH = 800;
    private final int HEIGHT = 400;
    private AdminController adminController;
    private Socket socket;

    public Application(AdminController clientController, ServiceAdmin serviceAdmin, Socket socket) {
        this.adminController = adminController;
        this.socket = socket;

        JFrame frame = new JFrame("Авторизация");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        frame.setLayout(new GridBagLayout());
        JLabel loginLable = new JLabel("Логин:");
        JLabel passwordLable = new JLabel("Пароль:");
        JTextField loginField = new JTextField("log");
        JPasswordField passwordField = new JPasswordField("pas");

        JButton loginButton = new JButton("Войти");
        loginButton.addActionListener(e -> {
            frame.dispose();
            serviceAdmin.setLogin(loginField.getText());
            serviceAdmin.setPassword(passwordField.getText());
            MainMenu mainMenu = new MainMenu(clientController, serviceAdmin, socket);
        });

        frame.add(loginLable);
        frame.add(loginField);
        frame.add(passwordLable);
        frame.add(passwordField);
        frame.add(loginButton);

        frame.revalidate();
    }
}
