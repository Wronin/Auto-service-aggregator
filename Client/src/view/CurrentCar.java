package view;

import controller.ClientController;
import model.Client;
import org.json.simple.JsonObject;
import org.json.simple.Jsoner;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.net.Socket;

public class CurrentCar {
    private ClientController clientController;
    private JPanel jPanel = new JPanel();

    public CurrentCar(ClientController clientController) {
        this.clientController = clientController;

        JFrame frame = new JFrame("Информация об автомобиле");
        frame.setSize(500, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        JButton back = new JButton("Назад");

        back.addActionListener(e -> {
            frame.dispose();
        });

        JLabel brand = new JLabel("Бренд");
        JLabel model = new JLabel("Модель");
        JLabel regNumber = new JLabel("Регистрационный номер");
        JLabel VINNumber = new JLabel("ВИН номер");

        JsonObject jsonObject = new JsonObject();
        try {
            FileReader reader = new FileReader("CurrentCar.json");
            jsonObject = (JsonObject) Jsoner.deserialize(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JTextField brandText = new JTextField((String) jsonObject.get("brand"));
        JTextField modelText = new JTextField((String) jsonObject.get("model"));
        JTextField regNumberText = new JTextField((String) jsonObject.get("regNumber"));
        JTextField VINNumberText = new JTextField((String) jsonObject.get("VINNumber"));

        frame.add(jPanel);

        GridBagLayout gridBagLayout = new GridBagLayout();
        jPanel.setLayout(gridBagLayout);

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.5;
        c.weighty = 0;
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 1;
        c.gridwidth = 1;
        jPanel.add(brand, c);

        c.gridx = 1;
        c.gridy = 0;
        jPanel.add(brandText, c);

        c.gridx = 0;
        c.gridy = 1;
        jPanel.add(model, c);

        c.gridx = 1;
        c.gridy = 1;
        jPanel.add(modelText, c);

        c.gridx = 0;
        c.gridy = 2;
        jPanel.add(regNumber, c);

        c.gridx = 1;
        c.gridy = 2;
        jPanel.add(regNumberText, c);

        c.gridx = 0;
        c.gridy = 3;
        jPanel.add(VINNumber, c);

        c.gridx = 1;
        c.gridy = 3;
        jPanel.add(VINNumberText, c);

        c.gridx = 0;
        c.gridy = 4;
        jPanel.add(back, c);

    }
}
