package view;

import controller.AdminController;
import model.ServiceAdmin;

import javax.swing.*;
import java.awt.*;
import java.net.Socket;

public class MainMenu {
    private AdminController adminController;
    private JPanel jPanel = new JPanel();

    public MainMenu(AdminController adminController, ServiceAdmin serviceAdmin, Socket socket) {
        this.adminController = adminController;

        JFrame frame = new JFrame("Главное меню");

        JButton findRequest = new JButton("Поиск заявок");
        JButton listRequest = new JButton("Список актуальных заявок");
        JButton chat = new JButton("Список диалогов");

        findRequest.addActionListener(e -> {
            frame.dispose();
            SearchRequest searchRequest = new SearchRequest(adminController, serviceAdmin, socket);
        });

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
        jPanel.add(findRequest, c);

        GridBagConstraints c1 = new GridBagConstraints();
        c1.weightx = 0;
        c1.weighty = 0;
        c1.gridx = 0;
        c1.gridy = 2;
        c1.gridheight = 1;
        c1.gridwidth = 2;
        jPanel.add(listRequest, c1);

        GridBagConstraints c2 = new GridBagConstraints();
        c2.weightx = 0;
        c2.weighty = 0;
        c2.gridx = 0;
        c2.gridy = 4;
        c2.gridheight = 1;
        c2.gridwidth = 2;
        jPanel.add(chat, c2);

        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

    }
}
