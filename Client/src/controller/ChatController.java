package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.Car;
import model.Chat;
import model.Client;

import java.util.ArrayList;
import java.util.Arrays;

public class ChatController {

    public ListView chatList;
    public TextField messageTextField;
    public TextArea textArea;

    public void init() {
        ArrayList<Chat> chats = ClientController.getInstance().getChatsForClient(Client.getSocket(), Client.getLogin(), Client.getPassword());
        String[] items = new String[chats.size()];
        for (int i = 0; i < chats.size(); i++) {
            items[i] = String.format("%s\n%s", chats.get(i).getCar().getRegNumber(), chats.get(i).getCarServiceName());
        }
        ObservableList<String> item = FXCollections.observableArrayList(items);
        chatList.setItems(item);
        chatList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                int idChat = 0, currentId = 0;
                for (Chat chat : chats) {
                    currentId = chat.getCurrentId((String) chatList.getSelectionModel().getSelectedItem(), chat);
                    if (currentId != 0){
                        idChat = currentId;
                    }
                }
                Chat currentChat = ClientController.getInstance().getCurrentChatForClient(Client.getSocket(), Client.getLogin(), Client.getPassword(), idChat);
                textArea.setText(currentChat.getMessagesToString(currentChat.getMessages()));
                textArea.setScrollTop(Double.MAX_VALUE);
            }
        });
    }

    public void sendMessage(ActionEvent actionEvent) {
        ArrayList<Chat> chats = ClientController.getInstance().getChatsForClient(Client.getSocket(), Client.getLogin(), Client.getPassword());
        int currentId = 0, idChat = 0;
        for (Chat chat : chats) {
            currentId = chat.getCurrentId((String) chatList.getSelectionModel().getSelectedItem(), chat);
            if (currentId != 0){
                idChat = currentId;
            }
        }
        ClientController.getInstance().sendClientMessage(Client.getSocket(), Client.getLogin(), Client.getPassword(), idChat, messageTextField.getText());
        Chat currentChat = ClientController.getInstance().getCurrentChatForClient(Client.getSocket(), Client.getLogin(), Client.getPassword(), idChat);
        textArea.setText(currentChat.getMessagesToString(currentChat.getMessages()));
        messageTextField.setText("");
        textArea.setScrollTop(Double.MAX_VALUE);
    }
}
