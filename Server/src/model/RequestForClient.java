package model;

import java.util.ArrayList;

public class RequestForClient {
    private int id;
    private int idAutoService;
    private int idRequest;
    private Car car;
    private String description;
    private String name;
    private String status;
    private ArrayList<Service> services;

    public RequestForClient(Car car, String description, String status) {
        this.car = car;
        this.description = description;
        this.status = status;
    }

    public RequestForClient(int idRequest, int idAutoService, String name) {
        this.idRequest = idRequest;
        this.idAutoService = idAutoService;
        this.name = name;
    }

    public RequestForClient(int id, int idAutoService, int idRequest, Car car, String name, String status) {
        this.id = id;
        this.idAutoService = idAutoService;
        this.idRequest = idRequest;
        this.car = car;
        this.name = name;
        this.status = status;
    }

    public RequestForClient(int id, Car car, String description, String name, String status, ArrayList<Service> services) {
        this.id = id;
        this.car = car;
        this.description = description;
        this.name = name;
        this.status = status;
        this.services = services;
    }


    public RequestForClient(int id, Car car, String description, String name, String status) {
        this.id = id;
        this.car = car;
        this.description = description;
        this.name = name;
        this.status = status;
    }

    public RequestForClient() {
    }

    public int getIdAutoService() {
        return idAutoService;
    }

    public int getIdRequest() {
        return idRequest;
    }

    public void setIdAutoService(int idAutoService) {
        this.idAutoService = idAutoService;
    }

    public void setIdRequest(int idRequest) {
        this.idRequest = idRequest;
    }

    public void addService(Service service) {
        this.services.add(service);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Car getCar() {
        return car;
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
