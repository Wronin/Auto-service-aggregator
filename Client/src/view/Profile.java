package view;

import controller.ClientController;
import model.Client;
import org.json.simple.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Iterator;

public class Profile {
    private ClientController clientController;
    private JPanel jPanel = new JPanel();
    private Socket socket;

    public Profile(ClientController clientController, Client client, Socket socket) {
        this.clientController = clientController;
        this.socket = socket;

        JFrame frame = new JFrame("Главное меню");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        JButton back = new JButton("Назад");

        String [] columName = {"Регистрационный номер"};
        ArrayList<String> carNumbers = clientController.getCarNumbers(socket, client.getLogin(), client.getPassword());

        Object[][] data = new Object[carNumbers.size()][];

        for (int i = 0; i < carNumbers.size(); i++) {
            data[i] = new String[]{carNumbers.get(i)};
        }
        JTable table = new JTable(data, columName);

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                clientController.getCar(socket, client.getLogin(), client.getPassword(), (String) table.getValueAt(table.getSelectedRow(), table.getSelectedColumn()));
                CurrentCar currentCar = new CurrentCar(clientController);
            }
        });

        back.addActionListener(e -> {
            frame.dispose();
            MainMenu mainMenu = new MainMenu(clientController, client, socket);
        });

        frame.add(jPanel);
        GridBagLayout gridBagLayout = new GridBagLayout();
        jPanel.setLayout(gridBagLayout);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.weighty = 0.9;
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        c.gridheight = 1;
        c.gridwidth = 1;
        jPanel.add(back, c);

        GridBagConstraints c1 = new GridBagConstraints();
        c1.fill = GridBagConstraints.HORIZONTAL;
        c1.weightx = 0;
        c1.weighty = 0.9;
        c1.gridx = 0;
        c1.gridy = 1;
        c1.gridheight = 1;
        c1.gridwidth = 1;
        jPanel.add(table, c1);
    }
}
