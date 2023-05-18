package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
    public Label error;
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
            Socket socket = new Socket("localhost", 3031);
            Client client;
            String content;
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            client = new Client(TextLogin.getText(), TextPassword.getText(), socket);
            content = "Client authorization";
            printWriter.println(content);
            printWriter.println(Client.getLogin());
            printWriter.println(Client.getPassword());
            content = bufferedReader.readLine();

            if (content.equals("Yes")) {
                root = FXMLLoader.load(getClass().getResource("../view/App.fxml"));
                stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else if (content.equals("error")) {
                error.setText("Неверно введен логин или пароль");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void onRegistrationButtonClick(ActionEvent e) {
        try {
            Socket socket = new Socket("localhost", 3031);
            Client client;
            String content;
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            client = new Client(TextLogin.getText(), TextPassword.getText(), socket);
            content = "Client registration";
            printWriter.println(content);
            printWriter.println(Client.getLogin());
            printWriter.println(Client.getPassword());
            content = bufferedReader.readLine();

            if (content.equals("Yes")) {
                root = FXMLLoader.load(getClass().getResource("../view/App.fxml"));
                stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else if (content.equals("error")) {
                error.setText("Ошибка регистрации");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @FXML
    protected void onRegisterButtonClick(ActionEvent e) {
        try {
            root = FXMLLoader.load(getClass().getResource("../view/registration.fxml"));
            stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    protected void onBackButtonClick(ActionEvent actionEvent) {
        try {
            root = FXMLLoader.load(getClass().getResource("../view/hello-view.fxml"));
            stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
