package view;

import controller.ClientController;
import model.Client;

import javax.swing.*;
import java.awt.*;
import java.net.Socket;

public class MainMenu extends JFrame {
    private ClientController clientController;
    private Socket socket;
    private JPanel jPanel = new JPanel();
    public MainMenu(ClientController clientController, Client client, Socket socket) {
        this.clientController = clientController;
        this.socket = socket;

        JFrame frame = new JFrame("Главное меню");

        JButton profile = new JButton("Профиль");
        JButton makeRequest = new JButton("Создать заявку");
        JButton showRequests = new JButton("Показать заявки");
        JButton search = new JButton("Поиск");
        JButton chat = new JButton("Чат");

        frame.add(jPanel);
        GridBagLayout gridBagLayout = new GridBagLayout();
        jPanel.setLayout(gridBagLayout);

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 2;
        c.gridwidth = 2;
        jPanel.add(profile, c);

        GridBagConstraints c1 = new GridBagConstraints();
        c1.weightx = 0;
        c1.weighty = 0;
        c1.gridx = 0;
        c1.gridy = 2;
        c1.gridheight = 1;
        c1.gridwidth = 2;
        jPanel.add(makeRequest, c1);

        GridBagConstraints c2 = new GridBagConstraints();
        c2.weightx = 0;
        c2.weighty = 0;
        c2.gridx = 0;
        c2.gridy = 4;
        c2.gridheight = 1;
        c2.gridwidth = 2;
        jPanel.add(showRequests, c2);

        GridBagConstraints c3 = new GridBagConstraints();
        c3.weightx = 0;
        c3.weighty = 0;
        c3.gridx = 0;
        c3.gridy = 6;
        c3.gridheight = 1;
        c3.gridwidth = 2;
        jPanel.add(search, c3);

        c3.weightx = 0;
        c3.weighty = 0;
        c3.gridx = 0;
        c3.gridy = 8;
        c3.gridheight = 1;
        c3.gridwidth = 2;
        jPanel.add(chat, c3);

        profile.addActionListener(e -> {
            frame.dispose();
            Profile profileWindow = new Profile(clientController, client, socket);
        });

        makeRequest.addActionListener(e -> {
            frame.dispose();
            Request request = new Request(clientController, client, socket);
        });

        showRequests.addActionListener(e -> {
            frame.dispose();
            ActualRequest actualRequest = new ActualRequest(clientController, client, socket);
        });

        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

    }
}
