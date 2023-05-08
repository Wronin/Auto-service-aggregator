package model;

import java.util.ArrayList;

public class RequestForClient {
    private int id;
    private String regNumber;
    private String description;
    private String name;
    private String status;
    private ArrayList<Service> services;

    public RequestForClient(int id, String regNumber, String description, String name, String status, ArrayList<Service> services) {
        this.id = id;
        this.regNumber = regNumber;
        this.description = description;
        this.name = name;
        this.status = status;
        this.services = services;
    }

    public RequestForClient(String regNumber, String description, String status) {
        this.regNumber = regNumber;
        this.description = description;
        this.status = status;
    }

    public RequestForClient(int id, String regNumber, String description, String name, String status) {
        this.id = id;
        this.regNumber = regNumber;
        this.description = description;
        this.name = name;
        this.status = status;
    }

    public void addService(Service service) {
        this.services.add(service);
    }

    public RequestForClient() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }
}
