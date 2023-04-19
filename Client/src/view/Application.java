package view;

import controller.ClientController;
import model.Client;

import javax.swing.*;
import java.awt.*;
import java.net.Socket;

public class Application extends JFrame{
    private final int WIDTH = 800;
    private final int HEIGHT = 400;
    private ClientController clientController;
    private Socket socket;

    public Application(ClientController clientController, Client client, Socket socket) {
        this.clientController = clientController;
        this.socket = socket;

        JFrame frame = new JFrame("Авторизация");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        frame.setLayout(new GridBagLayout());
        JLabel loginLable = new JLabel("Логин:");
        JLabel passvordLable = new JLabel("Пароль:");
        JTextField loginField = new JTextField(15);
        JPasswordField passwordField = new JPasswordField(15);

        loginField.setText("log1");
        passwordField.setText("pas1");

        JButton loginButton = new JButton("Войти");
        loginButton.addActionListener(e -> {
            frame.dispose();
            client.setLogin(loginField.getText());
            client.setPassword(passwordField.getText());
            MainMenu mainMenu = new MainMenu(clientController, client, socket);
        });

        frame.add(loginLable);
        frame.add(loginField);
        frame.add(passvordLable);
        frame.add(passwordField);
        frame.add(loginButton);

        frame.revalidate();
    }
}
