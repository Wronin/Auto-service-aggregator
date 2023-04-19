package view;

import controller.AdminController;
import model.Request;
import model.ServiceAdmin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Currency;

public class SearchRequest {
    private AdminController adminController;
    private Socket socket;
    private JPanel jPanel = new JPanel();
    public SearchRequest(AdminController adminController, ServiceAdmin serviceAdmin, Socket socket) {
        this.adminController = adminController;
        this.socket = socket;

        JFrame frame = new JFrame("Список заявок");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        JButton back = new JButton("Назад");

        ArrayList<Request> request = adminController.getAllAdminRequest(socket, serviceAdmin.getLogin(), serviceAdmin.getPassword());
        Object[][] data = new Object[request.size()][];

        for (int i = 0; i < request.size(); i++) {
            data[i] = new String[] {String.valueOf(request.get(i).getId()), request.get(i).getDescription(), request.get(i).getCar().getRegNumber(), request.get(i).getStatus().toString()};
        }
        String [] columnName = {"Номер заявки", "Описание проблемы", "Регистрационный номер", "Статус заявки"};

        TableModel model = new DefaultTableModel(data, columnName);
        JTable jTable = new JTable(model);

        jTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int id = Integer.parseInt(jTable.getValueAt(jTable.getSelectedRow(), 0).toString());
                CurrentRequest currentRequest = new CurrentRequest(adminController, serviceAdmin, socket, request.get(jTable.getSelectedRow()));
            }
        });

        frame.add(jPanel);

        GridBagLayout gridBagLayout = new GridBagLayout();
        jPanel.setLayout(gridBagLayout);

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        jPanel.add(back, c);

        c.gridx = 0;
        c.gridy = 1;
        jPanel.add(jTable, c);

    }
}
