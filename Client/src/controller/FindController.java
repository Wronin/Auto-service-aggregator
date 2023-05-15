package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.Car;
import model.CarService;
import model.Client;
import model.Service;

import java.util.ArrayList;

public class FindController {
    public ListView<String> carServiceList;
    public Label nameLabel;
    public Label descriptionLabel;
    public Label brandLabel;
    public Label serviceLabel;
    public ComboBox<String> carComboBox;
    public ComboBox<String> serviceComboBox;

    public void init() {
        ArrayList<CarService> carServices = ClientController.getInstance().getCarServices(Client.getSocket());
        String[] names = new String[carServices.size()];
        for (int i = 0; i < carServices.size(); i++) {
            names[i] = carServices.get(i).getName();
        }
        ObservableList<String> item = FXCollections.observableArrayList(names);
        carServiceList.setItems(item);
        carServiceList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                int idCarService = 0, currentId = 0;
                for (CarService carService : carServices) {
                    currentId = carService.getCurrentId(carServiceList.getSelectionModel().getSelectedItem(), carService);
                    if (currentId != 0) {
                        idCarService = currentId;
                    }
                }
                if (newValue != null) {
                    CarService carService = ClientController.getInstance().getCarServiceById(Client.getSocket(), idCarService);
                    nameLabel.setText(carService.getName());
                    descriptionLabel.setText(carService.getSpecification());
                    brandLabel.setText(carService.arrayBrandToString());
                    serviceLabel.setText(carService.arrayServiceToString());
                }
            }
        });

        ArrayList<Service> services = ClientController.getInstance().getAllServices(Client.getSocket());
        String[] items = new String[services.size()];
        for (int i = 0; i < services.size(); i++) {
            items[i] = services.get(i).getName();
        }
        ObservableList<String> serviceNames = FXCollections.observableArrayList(items);
        serviceComboBox.setItems(serviceNames);

        ArrayList<Car> cars = ClientController.getInstance().getAllBrands(Client.getSocket());
        items = new String[cars.size()];
        for (int i = 0; i < cars.size(); i++) {
            items[i] = cars.get(i).getBrand();
        }
        ObservableList<String> brandNames = FXCollections.observableArrayList(items);
        carComboBox.setItems(brandNames);
    }

    public void onFindButtonPressed(ActionEvent actionEvent) {
        int idService = 0;
        String tmp = serviceComboBox.getSelectionModel().getSelectedItem();
        ArrayList<Service> services = ClientController.getInstance().getAllServices(Client.getSocket());

        for (Service service : services) {
            if (service.getName().equals(tmp)) {
                idService = service.getId();
            }
        }

        ArrayList<CarService> resultSearch = ClientController.getInstance().getSearchResult(Client.getSocket(), idService, carComboBox.getSelectionModel().getSelectedItem());
        String[] names = new String[resultSearch.size()];
        for (int i = 0; i < resultSearch.size(); i++) {
            names[i] = resultSearch.get(i).getName();
        }
        ObservableList<String> item = FXCollections.observableArrayList(names);
        carServiceList.setItems(item);

    }
}
