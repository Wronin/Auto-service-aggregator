package model;

import java.util.ArrayList;

public class RequestForClient {
    private int id;
    private int idAutoService;
    private int idRequest;
    private String regNumber;
    private Car car;
    private String description;
    private String carServiceName;
    private String status;
    private ArrayList<Service> services;

    public RequestForClient(int id, Car car, String description, String carServiceName, String status, ArrayList<Service> services) {
        this.id = id;
        this.car = car;
        this.description = description;
        this.carServiceName = carServiceName;
        this.status = status;
        this.services = services;
    }
    public RequestForClient(int id, int idAutoService, int idRequest, String regNumber, String name, String status) {
        this.id = id;
        this.idAutoService = idAutoService;
        this.idRequest = idRequest;
        this.regNumber = regNumber;
        this.carServiceName = name;
        this.status = status;
    }

    public RequestForClient(int id, Car car, String description, String carServiceName, String status) {
        this.id = id;
        this.car = car;
        this.description = description;
        this.carServiceName = carServiceName;
        this.status = status;
    }

    public void addService(Service service) {
        this.services.add(service);
    }

    public RequestForClient() {
    }

    public int getIdAutoService() {
        return idAutoService;
    }

    public int getIdRequest() {
        return idRequest;
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setIdAutoService(int idAutoService) {
        this.idAutoService = idAutoService;
    }

    public void setIdRequest(int idRequest) {
        this.idRequest = idRequest;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCarServiceName() {
        if (!carServiceName.equals("null"))
            return carServiceName;
        else return "";
    }

    public void setCarServiceName(String carServiceName) {
        this.carServiceName = carServiceName;
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

    public String getServicesFromArray() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Service service : services) {
            if (!service.getName().equals("null")) {
                stringBuilder.append(service.getName());
                stringBuilder.append(", ");
            }
        }
//        stringBuilder.deleteCharAt(stringBuilder.length());
//        stringBuilder.deleteCharAt(stringBuilder.length());
        return stringBuilder.toString();
    }
}
