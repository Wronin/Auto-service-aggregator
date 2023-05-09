package model;

import java.util.ArrayList;

public class Request {
    private Client client;
    private ServiceAdmin serviceAdmin;
    private String description;
    private Car car;
    private Status status;
    private int id;
    private ArrayList<Service> services;

    public Request(Client client, String description, Car car, Status status) {
        this.client = client;
        this.description = description;
        this.car = car;
        this.status = status;
    }

    public Request(Client client, String description, Car car, ArrayList<Service> services, Status status) {
        this.client = client;
        this.description = description;
        this.car = car;
        this.services = services;
        this.status = status;
    }

    public Request(int id, String description, Car car, Status status) {
        this.id = id;
        this.description = description;
        this.car = car;
        this.status = status;
    }

    public Request() {
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ServiceAdmin getServiceAdmin() {
        return serviceAdmin;
    }

    public void setServiceAdmin(ServiceAdmin serviceAdmin) {
        this.serviceAdmin = serviceAdmin;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Status getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ArrayList<Service> getServices() {
        return services;
    }
    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }
}
