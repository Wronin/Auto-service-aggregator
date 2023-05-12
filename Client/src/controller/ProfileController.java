package controller;

import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Car;
import model.Client;

public class ProfileController {
    public TextField brand;
    public TextField model;
    public TextField vin;
    public TextField number;
    public ListView<String> carList;
    public Label brandLabel;
    public Label modelLabel;
    public Label vinLabel;
    public Label regLabel;
    public TextField editBrand;
    public TextField editModel;
    public TextField editVin;
    public TextField editNumber;
    private Parent root;
    private Scene scene;
    private Stage stage;
    @FXML
    private BorderPane borderPane;

    public ProfileController() {
    }

    public void init() {
        ObservableList<String> items = FXCollections.observableArrayList(ClientController.getInstance().getCarNumbers(Client.getSocket(), Client.getLogin(), Client.getPassword()));
        carList.setItems(items);
        carList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                Car car = ClientController.getInstance().getCar(Client.getSocket(), Client.getLogin(), Client.getPassword(), carList.getSelectionModel().getSelectedItem());
                brandLabel.setText(car.getBrand());
                modelLabel.setText(car.getModel());
                vinLabel.setText(car.getVINNumber());
                regLabel.setText(car.getRegNumber());
            }
        });
    }
    public void openAddCarWindow(javafx.event.ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("../view/addCar.fxml"));
            borderPane.setCenter(parent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void addCar(javafx.event.ActionEvent actionEvent) {
        ClientController.getInstance().addCar(Client.getSocket(), Client.getLogin(), Client.getPassword(), brand.getText(), model.getText(), vin.getText(), number.getText());
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("../view/App.fxml"));
            stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
//            borderPane.setCenter(FXMLLoader.load(getClass().getResource("../view/Profile.fxml")));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteCar(ActionEvent actionEvent) {
        Car car = ClientController.getInstance().getCar(Client.getSocket(), Client.getLogin(), Client.getPassword(), carList.getSelectionModel().getSelectedItem());
        ClientController.getInstance().deleteCar(Client.getSocket(), Client.getLogin(), Client.getPassword(), car.getBrand(), car.getModel(), car.getVINNumber(), car.getRegNumber());

        ObservableList<String> items = FXCollections.observableArrayList(ClientController.getInstance().getCarNumbers(Client.getSocket(), Client.getLogin(), Client.getPassword()));
        carList.setItems(items);
        brandLabel.setText("");
        modelLabel.setText("");
        vinLabel.setText("");
        regLabel.setText("");
    }

    public void openEditCarWindow(ActionEvent actionEvent) {
        try {
            Car car = ClientController.getInstance().getCar(Client.getSocket(), Client.getLogin(), Client.getPassword(), carList.getSelectionModel().getSelectedItem());
            Parent parent;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/editCar.fxml"));
            parent = loader.load();
            EditController editController = loader.getController();
            editController.init(car);
            borderPane.setCenter(parent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
