package model;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class AnswerAutoService {
    private int id;
    private int idAutoService;
    private int idRequest;
    private String regNumber;
    private String name;
    private String status;
    private ArrayList<Service> services;

    public AnswerAutoService(int id, int idRequest, String regNumber, String name, String status) {
        this.id = id;
        this.idRequest = idRequest;
        this.regNumber = regNumber;
        this.name = name;
        this.status = status;
    }
    public AnswerAutoService(int id, int idAutoService, int idRequest, String regNumber, String name, String status, ArrayList<Service> services) {
        this.id = id;
        this.idAutoService = idAutoService;
        this.idRequest = idRequest;
        this.regNumber = regNumber;
        this.name = name;
        this.status = status;
        this.services = services;
    }
    public AnswerAutoService(int id, int idAutoService, int idRequest, String regNumber, String name, String status) {
        this.id = id;
        this.idAutoService = idAutoService;
        this.idRequest = idRequest;
        this.regNumber = regNumber;
        this.name = name;
        this.status = status;
    }

    public AnswerAutoService() {
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

    public int getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(int idRequest) {
        this.idRequest = idRequest;
    }

    public int getIdAutoService() {
        return idAutoService;
    }

    public ArrayList<Service> getServices() {
        return services;
    }

    public void setIdAutoService(int idAutoService) {
        this.idAutoService = idAutoService;
    }

    public void setServices(ArrayList<Service> services) {
        this.services = services;
    }

    public String getServicesFromArray() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Service service : services) {
            if (!service.getName().equals("null")) {
                stringBuilder.append(service.getName());
                stringBuilder.append(" ");
            }
        }
        String collect = services.stream().map(Service::getName).collect(Collectors.joining(", "));
        return collect;
    }
}
