package model;

import java.security.PrivateKey;

public class Request {
    private int id;
    private ServiceAdmin serviceAdmin;
    private String description;
    private Car car;
    private Status status;
    private String statusString;

    public Request(ServiceAdmin serviceAdmin, String description, Car car, Status status) {
        this.serviceAdmin = serviceAdmin;
        this.description = description;
        this.car = car;
        this.status = status;
    }

    public Request(String description, Car car, Status status) {
        this.description = description;
        this.car = car;
        this.status = status;
    }

    public Request(int id, String description, Car car, Status status) {
        this.id = id;
        this.description = description;
        this.car = car;
        this.status = status;
    }

    public Request(int id, String description, Car car, String statusString) {
        this.id = id;
        this.description = description;
        this.car = car;
        this.statusString = statusString;
    }

    public Request() {
    }

    public String getStatusString() {
        return statusString;
    }

    public void setStatusString(String statusString) {
        this.statusString = statusString;
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
}
