package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class ApplicationController {
    @FXML
    private Pane newPane;
    @FXML
    private BorderPane bp;

    @FXML
    protected void onProfileButtonPressed() {
        loadProfile("Profile");
    }

    public void loadProfile(String name) {
        try {
            Parent parent = null;
            parent = FXMLLoader.load(getClass().getResource("../view/" + name + ".fxml"));
            bp.setCenter(parent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
