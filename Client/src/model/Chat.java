package model;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Chat {
    private int id;
    private Car car;
    private String carServiceName;
    private ArrayList<Message> messages;

    public Chat() {
    }

    public Chat(int id, Car car, String carServiceName) {
        this.id = id;
        this.car = car;
        this.carServiceName = carServiceName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setCarServiceName(String carServiceName) {
        this.carServiceName = carServiceName;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public int getId() {
        return id;
    }

    public Car getCar() {
        return car;
    }

    public String getCarServiceName() {
        return carServiceName;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public int getCurrentId(String line, Chat chat) {
        String[] split = line.split("\n");
        if (chat.car.getRegNumber().equals(split[0]) && chat.getCarServiceName().equals(split[1])) {
            return chat.getId();
        }
        return 0;
    }

    public String getMessagesToString(ArrayList<Message> messages) {
        StringBuilder message = new StringBuilder();
        for (Message mess : messages) {
            message.append(String.format("%s\n", mess.getMessage()));
        }
        return message.toString();
    }
}
