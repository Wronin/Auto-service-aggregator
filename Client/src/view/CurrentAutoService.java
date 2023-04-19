package view;

import controller.ClientController;
import model.CarService;
import model.Client;

import javax.swing.*;
import java.awt.*;
import java.net.Socket;

public class CurrentAutoService {
    private JPanel jPanel = new JPanel();

    public CurrentAutoService(ClientController clientController, Client client, Socket socket, CarService carService, int idAnswer) {
        JFrame frame = new JFrame();

        JButton back = new JButton("Назад");
        JLabel name = new JLabel(carService.getName());
        JLabel description = new JLabel(carService.getSpecification());
        JButton accept = new JButton("Принять");
        JButton deny = new JButton("Отказать");

        back.addActionListener(e -> {
            frame.dispose();
        });

        accept.addActionListener(e -> {
            frame.dispose();
            clientController.acceptRequest(socket, client.getLogin(), client.getPassword(), idAnswer);
        });

        frame.add(jPanel);
        GridBagLayout gridBagLayout = new GridBagLayout();
        jPanel.setLayout(gridBagLayout);

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        jPanel.add(back, c);

        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 1;
        jPanel.add(name, c);

        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 2;
        jPanel.add(description, c);

        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 3;
        jPanel.add(accept, c);

        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 4;
        jPanel.add(deny, c);

        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
