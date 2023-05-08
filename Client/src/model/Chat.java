package model;

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
}
