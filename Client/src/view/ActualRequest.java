package view;

import com.sun.tools.javac.Main;
import controller.ClientController;
import model.AnswerAutoService;
import model.CarService;
import model.Client;
import model.RequestForClient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

public class ActualRequest {
    private ClientController clientController;
    private Client client;
    private Socket socket;
    private JPanel jPanel = new JPanel();

    public ActualRequest(ClientController clientController, Client client, Socket socket) {
        this.clientController = clientController;
        this.client = client;
        this.socket = socket;

        JFrame frame = new JFrame("Актуальные заявки");

        JButton back = new JButton("Назад");
        JLabel sent = new JLabel("Отправленные заявки");
        JLabel received = new JLabel("Полученные ответы");


        ArrayList<RequestForClient> request = clientController.getAllClientRequest(socket, client.getLogin(), client.getPassword());
        Object[][] data = new Object[request.size()][];

        for (int i = 0; i < request.size(); i++) {
            if (request.get(i).getName() != null) {
                data[i] = new String[] {String.valueOf(request.get(i).getId()), request.get(i).getRegNumber(), request.get(i).getDescription(), request.get(i).getName(), request.get(i).getStatus()};
            } else {
                data[i] = new String[] {String.valueOf(request.get(i).getId()), request.get(i).getRegNumber(), request.get(i).getDescription(), " - ", request.get(i).getStatus()};
            }

        }
        String [] columnName = {"Номер заявки", "Регистрационный номер", "Описание проблемы", "Название автосервиса", "Статус заявки"};

        TableModel model = new DefaultTableModel(data, columnName);
        JTable jTable = new JTable(model);

        ArrayList<AnswerAutoService> answers = clientController.getAnswerAutoService(socket, client.getLogin(), client.getPassword());
        Object[][] data1 = new Object[answers.size()][];

        for (int i = 0; i < answers.size(); i++) {
            data1[i] = new String[] {String.valueOf(answers.get(i).getId()), answers.get(i).getRegNumber(), answers.get(i).getName(), answers.get(i).getStatus()};
        }
        String [] columnName1 = {"Номер заявки","Регистрационный номер", "Название автосервиса", "Статус заявки"};

        TableModel model1 = new DefaultTableModel(data1, columnName1);
        JTable jTable1 = new JTable(model1);

        back.addActionListener(e -> {
            frame.dispose();
            MainMenu mainMenu = new MainMenu(clientController, client, socket);
        });

        jTable1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                String name = (String) jTable1.getValueAt(jTable1.getSelectedRow(), 2);
                CarService carService = clientController.getCarServiceById(socket, 1);
                CurrentAutoService currentAutoService = new CurrentAutoService(clientController, client, socket, carService, Integer.parseInt(jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString()));
            }
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
        jPanel.add(sent, c);

        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 2;
        jPanel.add(jTable, c);

        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 3;
        jPanel.add(received, c);

        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 4;
        jPanel.add(jTable1, c);

        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
