package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import model.*;

import java.util.ArrayList;

public class CreateRequestController {

    private Scene scene;
    private Stage stage;
    public ComboBox carComboBox;
    public ComboBox servicesComboBox;
    public ListView servicesListView;
    public TextArea descriptionTextArea;
    public ArrayList<Service> services;
    public ArrayList<Service> servicesForRequest;

    public void init() {
        servicesForRequest = new ArrayList<>();
        ObservableList<String> items = FXCollections.observableArrayList(ClientController.getInstance().getCarNumbers(Client.getSocket(), Client.getLogin(), Client.getPassword()));
        carComboBox.setItems(items);

        services = ClientController.getInstance().getAllServices(Client.getSocket());
        String[] names = new String[services.size()];
        for (int i = 0; i < services.size(); i++) {
            names[i] = services.get(i).getName();
        }
        ObservableList<String> serviceNames = FXCollections.observableArrayList(names);
        servicesComboBox.setItems(serviceNames);
    }

    public void addServiceInListPressed(ActionEvent actionEvent) {
        String serviceName = (String) servicesComboBox.getSelectionModel().getSelectedItem();
        for (Service service : services)  {
            if (service.getName().equals(serviceName)) {
                servicesForRequest.add(
                        new Service(
                                service.getId()
                        )
                );
            }
        }
        servicesListView.getItems().add(serviceName);
    }

    public void addRequestPressed(ActionEvent actionEvent) {
        Car car = ClientController.getInstance().getCar(Client.getSocket(), Client.getLogin(), Client.getPassword(), (String) carComboBox.getSelectionModel().getSelectedItem());
        ClientController.getInstance().addRequestWithServices(Client.getSocket(), Client.getLogin(), Client.getPassword(), descriptionTextArea.getText(), car.getBrand(), car.getModel(), car.getVINNumber(), car.getRegNumber(), servicesForRequest);
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

    public void cancelAddRequest(ActionEvent actionEvent) {
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
}
