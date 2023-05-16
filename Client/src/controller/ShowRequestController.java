package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import model.AnswerAutoService;
import model.Client;
import model.RequestForClient;

import java.util.ArrayList;

public class ShowRequestController {
    public ComboBox statusRequestComboBox;
    public Label regNumLabel;
    public Label vinLabel;
    public Label brandLabel;
    public Label modelLabel;
    public Label descriptionLabel;
    public Label servicesLabel;
    public Label statusLabel;
    public Label autoServiceNameLabel;
    public ListView requestListView;
    public ListView answerRequestListView;
    public ArrayList<RequestForClient> requests;
    public ArrayList<RequestForClient> currentRequest;
    public ArrayList<AnswerAutoService> answerAutoServices;
    public ArrayList<AnswerAutoService> currentAnswerAutoServices;
    public AnswerAutoService answerAutoServiceForSend;

    public void init() {
        answerAutoServices = ClientController.getInstance().getAnswerAutoService(Client.getSocket(), Client.getLogin(), Client.getPassword());
        currentAnswerAutoServices = answerAutoServices;
        requests = ClientController.getInstance().getAllClientRequest(Client.getSocket(), Client.getLogin(), Client.getPassword());
        currentRequest = requests;

        String[] items = new String[requests.size()];
        for (int i = 0; i < requests.size(); i++) {
            items[i] = requests.get(i).getCar().getRegNumber();
        }
        ObservableList<String> regNumbers = FXCollections.observableArrayList(items);
        requestListView.setItems(regNumbers);
        requestListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                int currentId = requestListView.getSelectionModel().getSelectedIndex();
                if (newValue != null) {
                    regNumLabel.setText(currentRequest.get(currentId).getCar().getRegNumber());
                    vinLabel.setText(currentRequest.get(currentId).getCar().getVINNumber());
                    brandLabel.setText(currentRequest.get(currentId).getCar().getBrand());
                    modelLabel.setText(currentRequest.get(currentId).getCar().getModel());
                    descriptionLabel.setText(currentRequest.get(currentId).getDescription());
                    if (currentRequest.get(currentId).getServices() != null) {
                        servicesLabel.setText(currentRequest.get(currentId).getServicesFromArray());
                    } else {
                        servicesLabel.setText("");
                    }
                    statusLabel.setText(currentRequest.get(currentId).getStatus());
                    if (currentRequest.get(currentId).getCarServiceName() != null) {
                        autoServiceNameLabel.setText(currentRequest.get(currentId).getCarServiceName());
                    } else {
                        autoServiceNameLabel.setText("");
                    }

                    currentAnswerAutoServices = new ArrayList<>();
                    for (AnswerAutoService answerAutoService : answerAutoServices) {
                        if (answerAutoService.getIdRequest() == currentRequest.get(currentId).getId()) {
                            currentAnswerAutoServices.add(answerAutoService);
                        }
                    }
                    String[] items = new String[currentAnswerAutoServices.size()];
                    for (int i = 0; i < currentAnswerAutoServices.size(); i++) {
                        items[i] = currentAnswerAutoServices.get(i).getName();
                    }
                    ObservableList<String> autoServicesName = FXCollections.observableArrayList(items);
                    answerRequestListView.setItems(autoServicesName);
                }
            }
        });

        answerRequestListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                int currentAnswerId = answerRequestListView.getSelectionModel().getSelectedIndex();
                if (newValue != null) {
                    servicesLabel.setText(currentAnswerAutoServices.get(currentAnswerId).getServicesFromArray());
                    autoServiceNameLabel.setText(currentAnswerAutoServices.get(currentAnswerId).getName());
                    answerAutoServiceForSend = currentAnswerAutoServices.get(currentAnswerId);
                }
            }
        });

        items = new String[4];
        items[0] = "";
        items[1] = "Поиск";
        items[2] = "В прогрессе";
        items[3] = "Выполнено";
        ObservableList<String> serviceNames = FXCollections.observableArrayList(items);
        statusRequestComboBox.setItems(serviceNames);
        statusRequestComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                String status = (String) statusRequestComboBox.getSelectionModel().getSelectedItem();
                currentRequest = new ArrayList<>();

                switch (status) {
                    case "":
                        currentRequest = requests;
                        break;
                    case "Поиск":
                        for (RequestForClient request : requests) {
                            if (request.getStatus().equals("SEARCH")) {
                                currentRequest.add(request);
                            }
                        }
                        break;
                    case "В прогрессе":
                        for (RequestForClient request : requests) {
                            if (request.getStatus().equals("Waiting")) {
                                currentRequest.add(request);
                            }
                        }
                        break;
                    case "Выполнено":
                        for (RequestForClient request : requests) {
                            if (request.getStatus().equals("Done")) {
                                currentRequest.add(request);
                            }
                        }
                        break;
                }
                String[] items = new String[currentRequest.size()];
                for (int i = 0; i < currentRequest.size(); i++) {
                    items[i] = currentRequest.get(i).getCar().getRegNumber();
                }
                ObservableList<String> regNumbers = FXCollections.observableArrayList(items);
                requestListView.setItems(regNumbers);
            }
        });
    }

    public void acceptAnswer(ActionEvent actionEvent) {
        ClientController.getInstance().acceptRequest(Client.getSocket(), Client.getLogin(), Client.getPassword(), answerAutoServiceForSend.getIdAutoService(), answerAutoServiceForSend.getIdRequest(), answerAutoServiceForSend.getServices());
    }

    public void denyAnswer(ActionEvent actionEvent) {
    }
}
