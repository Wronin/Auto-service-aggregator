package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Car;
import model.Client;

public class EditController {
    public TextField editBrand;
    public TextField editModel;
    public TextField editVin;
    public TextField editNumber;
    public Label brandLabel;
    public Label modelLabel;
    public Label vinLabel;
    public Label regLabel;
    private Parent root;
    private Scene scene;
    private Stage stage;


    public void init(Car currentCar) {
        editBrand.setText(currentCar.getBrand());
        editModel.setText(currentCar.getModel());
        editVin.setText(currentCar.getVINNumber());
        editNumber.setText(currentCar.getRegNumber());
        brandLabel.setText(currentCar.getBrand());
        modelLabel.setText(currentCar.getModel());
        vinLabel.setText(currentCar.getVINNumber());
        regLabel.setText(currentCar.getRegNumber());
    }
    public void editCar(javafx.event.ActionEvent actionEvent) {
        ClientController.getInstance().updateCarInformation(Client.getSocket(), Client.getLogin(), Client.getPassword(),
                brandLabel.getText(), modelLabel.getText(), vinLabel.getText(), regLabel.getText(),
                editBrand.getText(), editModel.getText(), editVin.getText(), editNumber.getText());

        try {
            Parent parent = FXMLLoader.load(getClass().getResource("../view/App.fxml"));
            stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void back(javafx.event.ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/App.fxml"));
            Parent parent = loader.load();
            ApplicationController applicationController = loader.getController();
            applicationController.backFromEdit(actionEvent);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
