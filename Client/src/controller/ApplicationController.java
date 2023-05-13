package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ApplicationController {
    @FXML
    private Pane newPane;
    @FXML
    private BorderPane bp;

    private Scene scene;
    private Stage stage;

    @FXML
    protected void onProfileButtonPressed() {
        try {
            Parent parent;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Profile.fxml"));
            parent = loader.load();
            ProfileController profileController = loader.getController();
            profileController.init();
            bp.setCenter(parent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void backFromEdit(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("../view/App.fxml"));
            stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void openChatWindow(ActionEvent actionEvent) {
        try {
            Parent parent;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/chat.fxml"));
            parent = loader.load();
            ChatController chatController = loader.getController();
            chatController.init();
            bp.setCenter(parent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openFindWindow(ActionEvent actionEvent) {
        try {
            Parent parent;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/find.fxml"));
            parent = loader.load();
            FindController findController = loader.getController();
            findController.init();
            bp.setCenter(parent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
