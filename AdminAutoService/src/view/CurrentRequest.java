package view;

import controller.AdminController;
import model.Request;
import model.ServiceAdmin;

import javax.swing.*;
import java.awt.*;
import java.net.Socket;

public class CurrentRequest {
    private AdminController adminController;
    private Socket socket;
    private JPanel jPanel = new JPanel();

    public CurrentRequest(AdminController adminController, ServiceAdmin serviceAdmin, Socket socket, Request request) {
        this.adminController = adminController;
        this.socket = socket;

        JFrame frame = new JFrame("Текущая заявка");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        JLabel number = new JLabel("id");
        JTextField numberText = new JTextField();
        numberText.setText( "" + request.getId() + "");

        JLabel desc = new JLabel("Описание проблемы");
        JTextField descText = new JTextField(request.getDescription());

        JLabel regNum = new JLabel("Регистрационный номер авто");
        JTextField regNumText = new JTextField(request.getCar().getRegNumber());

        JLabel brand = new JLabel("Бренд");
        JTextField brandText = new JTextField(request.getCar().getBrand());

        JLabel model = new JLabel("Модель");
        JTextField modelText = new JTextField(request.getCar().getModel());

        JLabel status = new JLabel("Статус");
        JTextField statusText = new JTextField(request.getCar().getVINNumber());

        JButton accept = new JButton("Предложить свои услуги");
        JButton back = new JButton("Назад");

        accept.addActionListener(e -> {
            frame.dispose();
//            adminController.a
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

        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        jPanel.add(accept, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 1;
        c.gridwidth = 1;
        jPanel.add(number, c);

        c.gridx = 1;
        c.gridy = 1;
        c.gridheight = 1;
        c.gridwidth = 1;
        jPanel.add(numberText, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridheight = 1;
        c.gridwidth = 1;
        jPanel.add(desc, c);

        c.gridx = 1;
        c.gridy = 2;
        c.gridheight = 1;
        c.gridwidth = 1;
        jPanel.add(descText, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridheight = 1;
        c.gridwidth = 1;
        jPanel.add(regNum, c);

        c.gridx = 1;
        c.gridy = 3;
        c.gridheight = 1;
        c.gridwidth = 1;
        jPanel.add(regNumText, c);

        c.gridx = 0;
        c.gridy = 4;
        c.gridheight = 1;
        c.gridwidth = 1;
        jPanel.add(brand, c);

        c.gridx = 1;
        c.gridy = 4;
        c.gridheight = 1;
        c.gridwidth = 1;
        jPanel.add(brandText, c);

        c.gridx = 0;
        c.gridy = 5;
        c.gridheight = 1;
        c.gridwidth = 1;
        jPanel.add(model, c);

        c.gridx = 1;
        c.gridy = 5;
        c.gridheight = 1;
        c.gridwidth = 1;
        jPanel.add(modelText, c);

        c.gridx = 0;
        c.gridy = 6;
        c.gridheight = 1;
        c.gridwidth = 1;
        jPanel.add(status, c);

        c.gridx = 1;
        c.gridy = 6;
        c.gridheight = 1;
        c.gridwidth = 1;
        jPanel.add(statusText, c);

    }
}
