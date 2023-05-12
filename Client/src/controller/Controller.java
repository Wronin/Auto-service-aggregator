package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Controller {
    private Parent root;
    private Scene scene;
    private Stage stage;
    @FXML
    private TextField TextLogin;
    @FXML
    private PasswordField TextPassword;
    @FXML
    protected void onHelloButtonClick(ActionEvent e) {
        try {
            Socket socket = new Socket("localhost", 3030);
            Client client = new Client(TextLogin.getText(), TextPassword.getText(), socket);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(Client.getLogin());
            printWriter.println(Client.getPassword());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String content = bufferedReader.readLine();
            System.out.println(content);

            if (content.equals("Yes")) {
                root = FXMLLoader.load(getClass().getResource("../view/App.fxml"));
                stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
