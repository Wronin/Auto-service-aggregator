package view;

import com.sun.tools.javac.Main;
import controller.ClientController;
import model.Car;
import model.Client;
import org.json.simple.JSONObject;
import org.json.simple.JsonObject;
import org.json.simple.Jsoner;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.Socket;

public class Request {

    private ClientController clientController;
    private Socket socket;
    private JPanel jPanel = new JPanel();

    public Request(ClientController clientController, Client client, Socket socket) {
        this.clientController = clientController;
        this.socket = socket;

        JFrame frame = new JFrame();
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        JComboBox carNumbers = new JComboBox<>(clientController.getCarNumbers(socket, client.getLogin(), client.getPassword()).toArray(new String[0]));
        JTextArea textArea = new JTextArea(4, 20);
        JScrollPane scrollPane = new JScrollPane(textArea);
        JButton sendRequest = new JButton("Отправить");
        JButton back = new JButton("Назад");

        back.addActionListener(e -> {
            frame.dispose();
            MainMenu mainMenu = new MainMenu(clientController, client, socket);
        });

        sendRequest.addActionListener(e -> {
            frame.dispose();
            clientController.getCar(socket, client.getLogin(), client.getPassword(), carNumbers.getSelectedItem().toString());
            JsonObject jsonObject = new JsonObject();
            try {
                FileReader reader = new FileReader("CurrentCar.json");
                jsonObject = (JsonObject) Jsoner.deserialize(reader);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            clientController.addRequest(socket, client.getLogin(), client.getPassword(), textArea.getText(), (String) jsonObject.get("brand"), (String) jsonObject.get("model"), (String) jsonObject.get("VINNumber"), (String) jsonObject.get("regNumber"));
            MainMenu mainMenu = new MainMenu(clientController, client, socket);
        });

        frame.add(jPanel);
        GridBagLayout gridBagLayout = new GridBagLayout();
        jPanel.setLayout(gridBagLayout);

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        jPanel.add(carNumbers, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 2;
        c.gridwidth = 2;
        jPanel.add(scrollPane, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridheight = 1;
        c.gridwidth = 2;
        jPanel.add(sendRequest, c);

        c.gridx = 0;
        c.gridy = 4;
        c.gridheight = 1;
        c.gridwidth = 2;
        jPanel.add(back, c);

    }
}
